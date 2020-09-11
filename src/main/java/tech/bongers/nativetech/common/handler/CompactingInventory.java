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
package tech.bongers.nativetech.common.handler;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;

@SuppressWarnings("ConstantConditions")
public class CompactingInventory extends CraftingInventory {

    private final ItemStack[] stackList;

    public CompactingInventory(final int width, final int height) {
        super(null, width, height);
        stackList = new ItemStack[width * height];
        Arrays.fill(stackList, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return slot >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList[slot];
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int slot, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int slot, @Nonnull ItemStack stack) {
        stackList[slot] = stack;
    }
}