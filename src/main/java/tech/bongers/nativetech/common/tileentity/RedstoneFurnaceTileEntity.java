/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of NativeTech.
 *
 *     NativeTech is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     NativeTech is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with NativeTech. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.nativetech.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import tech.bongers.nativetech.common.container.RedstoneFurnaceContainer;
import tech.bongers.nativetech.common.handler.NativeItemHandler;
import tech.bongers.nativetech.common.util.BonusBlockProperties;
import tech.bongers.nativetech.common.util.FuelProperties;
import tech.bongers.nativetech.common.util.Reference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static tech.bongers.nativetech.common.util.NativeProperties.REDSTONE_FURNACE;

public class RedstoneFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int MAX_BONUS_BLOCKS = 16;

    private final NativeItemHandler inventory;
    private int currentSmeltTime;
    private int currentBurnTime;
    private int maxSmeltTime;
    private int maxBurnTime;

    public RedstoneFurnaceTileEntity() {
        this(NativeTileEntity.REDSTONE_FURNACE_TILE_ENTITY.get());
    }

    private RedstoneFurnaceTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new NativeItemHandler(3);
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new RedstoneFurnaceContainer(id, playerInventory, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Reference.MOD_ID + "." + REDSTONE_FURNACE);
    }

    @Override
    public void tick() {
        boolean isBurningOnStart = isBurning();
        boolean dirty = false;
        if (isBurning()) {
            --currentBurnTime;
        }

        if (world != null && !world.isRemote) {
            final ItemStack itemstack = this.inventory.getStackInSlot(0);
            final ItemStack fuelStack = this.inventory.getStackInSlot(1);

            if (this.isBurning() || !itemstack.isEmpty() && !fuelStack.isEmpty()) {
                final IRecipe<?> recipe = getRecipe(itemstack);
                final int bonusBlocks = getBonusBlocksBeneath(getPos());

                if (!isBurning() && recipe != null && !fuelStack.isEmpty() && bonusBlocks > 0) {

                    final int smeltSpeedBonus = MathHelper.clamp(MathHelper.clamp(bonusBlocks, 1, MAX_BONUS_BLOCKS) / 4, 1, 4);
                    maxSmeltTime = FuelProperties.getSmeltTimeForFuel(fuelStack.getItem()) / smeltSpeedBonus;
                    maxBurnTime = (FuelProperties.getBurnTimeForFuel(fuelStack.getItem()) * MathHelper.clamp(bonusBlocks, 1, MAX_BONUS_BLOCKS)) / smeltSpeedBonus;

                    currentBurnTime = maxBurnTime;
                    fuelStack.shrink(1);
                    dirty = true;

                } else if (isBurning() && bonusBlocks == 0) {
                    currentSmeltTime = 0;
                }

                if (isBurning() && recipe != null) {
                    ++currentSmeltTime;
                    if (currentSmeltTime == maxSmeltTime) {
                        currentSmeltTime = 0;
                        smelt(recipe, bonusBlocks);
                        dirty = true;
                    }
                }

            } else if (currentSmeltTime > 0) {
                this.currentSmeltTime = MathHelper.clamp(currentSmeltTime - 2, 0, maxSmeltTime);
            }

            if (isBurningOnStart != isBurning()) {
                dirty = true;
                world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.LIT, isBurning()));
            }
        }

        if (dirty) {
            markDirty();
        }
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        inventory.setNonNullList(inv);
        currentSmeltTime = nbt.getInt("CurrentSmeltTime");
        currentBurnTime = nbt.getInt("CurrentBurnTime");
        maxSmeltTime = nbt.getInt("MaxSmeltTime");
        maxBurnTime = nbt.getInt("MaxBurnTime");
    }

    @Override
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
        ItemStackHelper.saveAllItems(nbt, inventory.toNonNullList());
        nbt.putInt("CurrentSmeltTime", currentSmeltTime);
        nbt.putInt("CurrentBurnTime", currentBurnTime);
        nbt.putInt("MaxSmeltTime", maxSmeltTime);
        nbt.putInt("MaxBurnTime", maxBurnTime);
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, final Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(() -> inventory));
    }

    @Nullable
    private BlastingRecipe getRecipe(final ItemStack stack) {
        if (stack != null) {
            final Set<IRecipe<?>> recipes = findBlastingRecipes(world);
            for (IRecipe<?> iRecipe : recipes) {
                BlastingRecipe recipe = (BlastingRecipe) iRecipe;
                if (recipe.matches(new RecipeWrapper(inventory), world)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    private Set<IRecipe<?>> findBlastingRecipes(final World world) {
        return world == null
                ? Collections.emptySet()
                : world.getRecipeManager().getRecipes()
                .stream()
                .filter(recipe -> recipe.getType() == IRecipeType.BLASTING)
                .collect(Collectors.toSet());
    }

    private int getBonusBlocksBeneath(final BlockPos pos) {
        int bonusBlocks = 0;
        if (world != null && !world.isRemote) {
            BlockPos blockPos = pos;
            final List<BonusBlockProperties> properties = BonusBlockProperties.forBlock(getBlockState().getBlock());
            for (BonusBlockProperties property : properties) {
                while (bonusBlocks < MAX_BONUS_BLOCKS && world.getBlockState(blockPos.down()).isIn(property.getBonusBlock())) {
                    bonusBlocks += property.getBonus();
                    blockPos = blockPos.down();
                }
            }
        }
        return bonusBlocks;
    }

    private void smelt(@Nullable final IRecipe<?> recipe, final int bonusBlocks) {
        if (recipe != null) {
            final ItemStack outputStack = recipe.getRecipeOutput().copy();
            final int count = bonusBlocks >= 16 ? 2 : 1;
            outputStack.setCount(count);
            inventory.insertItem(2, outputStack, false);
            inventory.decreaseStackSize(0, 1);
        }
    }

    /* GETTERS */
    public NativeItemHandler getInventory() {
        return inventory;
    }

    public int getCurrentSmeltTime() {
        return currentSmeltTime;
    }

    public int getCurrentBurnTime() {
        return currentBurnTime;
    }

    public int getMaxSmeltTime() {
        return maxSmeltTime;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    private boolean isBurning() {
        return this.currentBurnTime > 0;
    }

    /* SETTERS */
    public void setCurrentSmeltTime(final int currentSmeltTime) {
        this.currentSmeltTime = currentSmeltTime;
    }

    public void setCurrentBurnTime(final int currentBurnTime) {
        this.currentBurnTime = currentBurnTime;
    }

    public void setMaxSmeltTime(final int maxSmeltTime) {
        this.maxSmeltTime = maxSmeltTime;
    }

    public void setMaxBurnTime(final int maxBurnTime) {
        this.maxBurnTime = maxBurnTime;
    }
}
