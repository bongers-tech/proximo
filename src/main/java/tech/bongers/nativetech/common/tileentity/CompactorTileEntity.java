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
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import tech.bongers.nativetech.common.container.CompactorContainer;
import tech.bongers.nativetech.common.handler.CompactingInventory;
import tech.bongers.nativetech.common.handler.NativeItemHandler;

import javax.annotation.Nullable;
import java.util.Set;

import static tech.bongers.nativetech.common.util.NativeProperties.ACTIVE;
import static tech.bongers.nativetech.common.util.NativeProperties.COMPACTOR;

public class CompactorTileEntity extends AbstractNativeTileEntity {

    private int currentIndex = 0;
    private int process = 0;

    public CompactorTileEntity() {
        super(NativeTileEntity.COMPACTOR_TILE_ENTITY.get(), new NativeItemHandler(18));
    }

    @Override
    protected String getItemName() {
        return COMPACTOR;
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new CompactorContainer(id, playerInventory, this);
    }

    @Override
    public void tick() { // Refactor method to a cleaner solution
        boolean isActivatedOnStart = isActivated();
        boolean dirty = false;
        if (world != null && !world.isRemote) {

            if (getInventory().hasItems() && !isActivated()) {
                final ItemStack itemStack = getInventory().getStackInSlot(currentIndex);
                if (itemStack.isEmpty() || getRecipe(itemStack) == null) {
                    currentIndex = currentIndex < 8 ? currentIndex + 1 : 0;
                } else if (getRecipe(itemStack) != null) {
                    process = 1;
                }
            } else {
                ++process;
                if (process == 34) {
                    process = 0;
                    dirty = true;
                    final ICraftingRecipe recipe = getRecipe(getInventory().getStackInSlot(currentIndex));
                    if (recipe != null) {
                        getInventory().insertItem(currentIndex + 9, recipe.getRecipeOutput().copy(), false);
                        getInventory().decreaseStackSize(currentIndex, 9);
                    }
                }
            }

            if (isActivatedOnStart != isActivated()) {
                dirty = true;
                world.setBlockState(getPos(), getBlockState().with(ACTIVE, isActivated()));
            }
        }

        if (dirty) {
            markDirty();
        }
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt) {
        super.read(state, nbt);
        currentIndex = nbt.getInt("CurrentIndex");
        process = nbt.getInt("Process");
    }

    @Override
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
        nbt.putInt("CurrentIndex", currentIndex);
        nbt.putInt("Process", process);
        return nbt;
    }

    @Nullable
    private ICraftingRecipe getRecipe(final ItemStack stack) {
        if (world != null && stack != null) {

            // Improve logic to also include 2x2 crafting recipes
            final CompactingInventory reversion = new CompactingInventory(1, 1);
            final CompactingInventory compacting = new CompactingInventory(3, 3);

            for (int i = 0, n = MathHelper.clamp(stack.getCount(), 0, 9); i < n; i++) {
                compacting.setInventorySlotContents(i, stack);
            }
            for (int i = 0, n = MathHelper.clamp(stack.getCount(), 0, 1); i < n; i++) {
                reversion.setInventorySlotContents(i, stack);
            }

            final Set<IRecipe<?>> recipes = findRecipesForType(world, IRecipeType.CRAFTING);
            for (IRecipe<?> recipe : recipes) {
                if (recipe instanceof ICraftingRecipe && ((ICraftingRecipe) recipe).matches(compacting, world)) {
                    final ICraftingRecipe craftingRecipe = (ICraftingRecipe) recipe;

                    // Must include check to not match reversion (e.g. ingot to nugget)
                    if (craftingRecipe.matches(compacting, world) && !craftingRecipe.matches(reversion, world)) {
                        final ItemStack result = craftingRecipe.getCraftingResult(compacting);
                        if (!result.isEmpty()) {
                            return craftingRecipe;
                        }
                    }
                }
            }
        }
        return null;
    }

    /* GETTERS */
    public int getProcess() {
        return process;
    }

    public boolean isActivated() {
        return process > 0;
    }

    /* SETTERS */
    public void setProcess(int process) {
        this.process = process;
    }
}
