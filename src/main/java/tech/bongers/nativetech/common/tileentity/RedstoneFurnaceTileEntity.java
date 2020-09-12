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
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import tech.bongers.nativetech.common.container.RedstoneFurnaceContainer;
import tech.bongers.nativetech.common.item.handler.NativeItemHandler;
import tech.bongers.nativetech.common.util.BonusBlockProperties;
import tech.bongers.nativetech.common.util.FuelProperties;

import java.util.List;

import static tech.bongers.nativetech.common.util.NativeProperties.ACTIVE;
import static tech.bongers.nativetech.common.util.NativeProperties.REDSTONE_FURNACE;
import static tech.bongers.nativetech.common.util.RecipeUtil.getRecipeForInventoryAndType;

public class RedstoneFurnaceTileEntity extends AbstractNativeTileEntity {

    public static final int MAX_BONUS_BLOCKS = 16;

    private int currentSmeltTime;
    private int currentBurnTime;
    private int maxSmeltTime;
    private int maxBurnTime;

    public RedstoneFurnaceTileEntity() {
        super(NativeTileEntity.REDSTONE_FURNACE_TILE_ENTITY.get(), new NativeItemHandler(2, 1).setSlotAsFuelSlot(1));
    }

    @Override
    protected String getItemName() {
        return REDSTONE_FURNACE;
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new RedstoneFurnaceContainer(id, playerInventory, this);
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt) {
        super.read(state, nbt);
        currentSmeltTime = nbt.getInt("CurrentSmeltTime");
        currentBurnTime = nbt.getInt("CurrentBurnTime");
        maxSmeltTime = nbt.getInt("MaxSmeltTime");
        maxBurnTime = nbt.getInt("MaxBurnTime");
    }

    @Override
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
        nbt.putInt("CurrentSmeltTime", currentSmeltTime);
        nbt.putInt("CurrentBurnTime", currentBurnTime);
        nbt.putInt("MaxSmeltTime", maxSmeltTime);
        nbt.putInt("MaxBurnTime", maxBurnTime);
        return nbt;
    }

    @Override
    public void tick() {
        boolean isBurningOnStart = isBurning();
        boolean dirty = false;
        if (isBurning()) {
            --currentBurnTime;
        }

        if (world != null && !world.isRemote) {
            final ItemStack itemstack = getInventory().getStackInSlot(0);
            final ItemStack fuelStack = getInventory().getStackInSlot(1);

            if (this.isBurning() || !itemstack.isEmpty() && !fuelStack.isEmpty()) {
                final IRecipe<?> recipe = getRecipeForInventoryAndType(world, getInventory(), IRecipeType.BLASTING);
                final int bonusBlocks = getBonusBlocks(getPos());

                if (canSmelt(recipe, bonusBlocks)) {
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
                            smelt(recipe, bonusBlocks);
                            dirty = true;
                        }
                    }
                }
            } else if (currentSmeltTime > 0) {
                this.currentSmeltTime = MathHelper.clamp(currentSmeltTime - 2, 0, maxSmeltTime);
            }

            if (isBurningOnStart != isBurning()) {
                dirty = true;
                world.setBlockState(getPos(), getBlockState().with(ACTIVE, isBurning()));
            }
        }

        if (dirty) {
            markDirty();
        }
    }

    private int getBonusBlocks(final BlockPos pos) {
        int bonusBlocks = 0;
        if (world != null && !world.isRemote) {
            BlockPos blockPos = pos;
            final List<BonusBlockProperties> properties = BonusBlockProperties.forBlock(getBlockState().getBlock());
            for (BonusBlockProperties property : properties) {
                for (Direction direction : Direction.values()) {
                    while (bonusBlocks < MAX_BONUS_BLOCKS && world.getBlockState(blockPos.offset(direction)).isIn(property.getBonusBlock())) {
                        bonusBlocks += property.getBonus();
                        blockPos = blockPos.down();
                    }
                    blockPos = pos;
                }
            }
        }
        return bonusBlocks;
    }

    private void smelt(final IRecipe<?> recipe, final int bonusBlocks) {
        if (recipe != null) {
            final ItemStack outputStack = recipe.getRecipeOutput().copy();
            final int count = bonusBlocks >= 16 ? 2 : 1;

            currentSmeltTime = 0;
            outputStack.setCount(count);
            getInventory().insertItem(2, outputStack, false);
            getInventory().decreaseStackSize(0, 1);
        }
    }

    private boolean canSmelt(final IRecipe<?> recipe, final int bonusBlocks) {
        final int count = bonusBlocks >= 16 ? 2 : 1;
        final ItemStack outputSlot = getInventory().getStackInSlot(2);
        return (outputSlot.isEmpty()
                || (recipe != null
                && outputSlot.getItem() == recipe.getRecipeOutput().getItem()
                && outputSlot.getCount() + count <= outputSlot.getMaxStackSize()));
    }

    /* GETTERS */
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
