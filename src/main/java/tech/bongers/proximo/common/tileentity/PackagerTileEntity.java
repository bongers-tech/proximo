/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of Proximo.
 *
 *     Proximo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Proximo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Proximo. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.proximo.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import tech.bongers.proximo.common.container.PackagerContainer;
import tech.bongers.proximo.common.inventory.PackagerInventory;
import tech.bongers.proximo.common.item.handler.ProximoItemHandler;

import static tech.bongers.proximo.common.util.ProximoProperties.ACTIVE;
import static tech.bongers.proximo.common.util.ProximoProperties.PACKAGER;
import static tech.bongers.proximo.common.util.RecipeUtil.getCompactingRecipe;

public class PackagerTileEntity extends AbstractProximoTileEntity {

    public static final int PROCESSING_TIME = 56;

    private IRecipe<?> recipe;
    private int currentIndex = 0;
    private int process = 0;

    public PackagerTileEntity() {
        super(ProximoTileEntity.PACKAGER_TILE_ENTITY.get(), new ProximoItemHandler(9, 9));
    }

    @Override
    protected String getItemName() {
        return PACKAGER;
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new PackagerContainer(id, playerInventory, this);
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

    @Override
    public void tick() {
        boolean isActivatedOnStart = isActivated();
        boolean dirty = false;

        if (world != null && !world.isRemote) {
            if (getInventory().hasItems() && !isActivated()) {
                findRecipeForInput();
            } else {
                process = MathHelper.clamp(++process, 0, PROCESSING_TIME);
                if (process == PROCESSING_TIME) {
                    dirty = compact();
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

    private void findRecipeForInput() {
        final ItemStack itemStack = getInventory().getStackInSlot(currentIndex);
        final PackagerInventory smallGrid = new PackagerInventory(itemStack, 2, 2);
        final PackagerInventory largeGrid = new PackagerInventory(itemStack, 3, 3);

        IRecipe<?> compactingRecipe = getCompactingRecipe(world, itemStack, largeGrid);
        if (compactingRecipe == null) {
            compactingRecipe = getCompactingRecipe(world, itemStack, smallGrid);
        }

        if (itemStack.isEmpty() || compactingRecipe == null) {
            currentIndex = currentIndex < 8 ? currentIndex + 1 : 0;
        } else {
            recipe = compactingRecipe;
        }
    }

    private boolean compact() {
        if (recipe != null) {
            for (int i = 0; i < 9; i++) {
                final int index = i + 9;
                final ItemStack stackInSlot = getInventory().getStackInSlot(index);
                if (stackInSlot.isEmpty()) {
                    doCompact(index);
                    return true;
                } else if (stackInSlot.getItem() == recipe.getRecipeOutput().getItem()) {
                    final int stackSize = stackInSlot.getMaxStackSize();
                    if (stackInSlot.getCount() + 1 <= stackSize) {
                        doCompact(index);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void doCompact(final int index) {
        getInventory().insertItem(index, recipe.getRecipeOutput().copy(), false);
        getInventory().decreaseStackSize(currentIndex, recipe.getIngredients().size());
        recipe = null;
        process = 0;
    }

    /* GETTERS */
    public int getProcess() {
        return process;
    }

    public boolean isActivated() {
        return recipe != null;
    }

    /* SETTERS */
    public void setProcess(int process) {
        this.process = process;
    }
}
