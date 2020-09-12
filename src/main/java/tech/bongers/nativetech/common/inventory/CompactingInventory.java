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
package tech.bongers.nativetech.common.inventory;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.IntStream;

@SuppressWarnings("ConstantConditions")
public class CompactingInventory extends CraftingInventory {

    private final ItemStack[] stackList;

    public CompactingInventory(final ItemStack itemStack, final int width, final int height) {
        super(null, width, height);
        stackList = new ItemStack[width * height];
        Arrays.fill(stackList, ItemStack.EMPTY);
        for (int i = 0, n = MathHelper.clamp(itemStack.getCount(), 0, (width * height)); i < n; i++) {
            setInventorySlotContents(i, itemStack);
        }
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

    public boolean hasFullGrid() {
        return IntStream.range(0, stackList.length).noneMatch(i -> stackList[i].isEmpty());
    }
}