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
import net.minecraft.item.Items;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import tech.bongers.nativetech.common.container.RedstoneGeneratorContainer;
import tech.bongers.nativetech.common.handler.RedstoneGeneratorItemHandler;
import tech.bongers.nativetech.common.util.Reference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class RedstoneGeneratorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int MAX_SMELT_TIME = 50;
    public static final int MAX_BURN_TIME = 100;

    private final RedstoneGeneratorItemHandler inventory;
    private int currentSmeltTime;
    private int currentBurnTime;

    public RedstoneGeneratorTileEntity() {
        this(NativeTileEntity.REDSTONE_GENERATOR_TILE_ENTITY.get());
    }

    private RedstoneGeneratorTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new RedstoneGeneratorItemHandler(3);
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new RedstoneGeneratorContainer(id, playerInventory, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Reference.MOD_ID + ".redstone_generator");
    }

    @Override
    public void tick() { // Rewrite method. Should not be as complex as it is now
        boolean dirty = false;

        if (world != null && !world.isRemote) {
            if (world.isBlockPowered(getPos())) {
                final BlastingRecipe recipe = getRecipe(inventory.getStackInSlot(0));
                final ItemStack fuelStack = inventory.getStackInSlot(1);

                if (fuelStack.getItem() == Items.REDSTONE || (fuelStack.isEmpty() && currentBurnTime != 0)) {
                    if (recipe != null && currentBurnTime == 0) {
                        currentBurnTime = MAX_BURN_TIME;
                        inventory.decreaseStackSize(1, 1);
                    } else if (currentBurnTime == 0) {
                        dirty = true;
                        world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.LIT, false));
                    }

                    if (currentBurnTime != 0) {
                        dirty = true;
                        currentBurnTime--;
                        world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.LIT, true));
                    }
                }

                if (recipe != null && getBlockState().get(BlockStateProperties.LIT)) {
                    dirty = true;
                    if (currentSmeltTime != MAX_SMELT_TIME) {
                        currentSmeltTime++;
                    } else {
                        currentSmeltTime = 0;
                        final ItemStack output = recipe.getRecipeOutput();
                        output.setCount(2);
                        inventory.insertItem(2, output.copy(), false);
                        inventory.decreaseStackSize(0, 1);
                    }
                } else if (getBlockState().get(BlockStateProperties.LIT)) {
                    dirty = true;
                    currentSmeltTime = 0;
                    world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.LIT, false));
                }
            } else if (getBlockState().get(BlockStateProperties.LIT)) {
                dirty = true;
                currentSmeltTime = 0;
                world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.LIT, false));
            }
        }

        if (dirty) {
            markDirty();
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        inventory.setNonNullList(inv);
        currentSmeltTime = nbt.getInt("CurrentSmeltTime");
    }

    @Override
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
        ItemStackHelper.saveAllItems(nbt, inventory.toNonNullList());
        nbt.putInt("CurrentSmeltTime", currentSmeltTime);
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> inventory));
    }

    public IItemHandlerModifiable getInventory() {
        return inventory;
    }

    public int getCurrentBurnTime() {
        return currentBurnTime;
    }

    public void setCurrentBurnTime(final int currentBurnTime) {
        this.currentBurnTime = currentBurnTime;
    }

    public int getCurrentSmeltTime() {
        return currentSmeltTime;
    }

    public void setCurrentSmeltTime(final int currentSmeltTime) {
        this.currentSmeltTime = currentSmeltTime;
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
}
