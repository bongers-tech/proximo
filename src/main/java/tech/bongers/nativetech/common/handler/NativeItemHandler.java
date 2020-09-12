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

import com.sun.istack.internal.NotNull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import tech.bongers.nativetech.common.inventory.InventoryType;

import static tech.bongers.nativetech.common.inventory.InventoryType.INPUT;
import static tech.bongers.nativetech.common.inventory.InventoryType.OUTPUT;

public class NativeItemHandler extends ItemStackHandler {

    private final InventoryType inventoryType;

    public NativeItemHandler(final int size, final InventoryType inventoryType) {
        super(size);
        this.inventoryType = inventoryType;
    }

    public boolean hasItems() {
        return !stacks.isEmpty();
    }

    public NonNullList<ItemStack> toNonNullList() {
        NonNullList<ItemStack> items = NonNullList.create();
        items.addAll(stacks);
        return items;
    }

    public void setNonNullList(final NonNullList<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            if (itemStacks.size() != getSlots()) {
                throw new IndexOutOfBoundsException("NonNullList must be same size as ItemStackHandler!");
            }

            for (int index = 0; index < itemStacks.size(); index++) {
                stacks.set(index, itemStacks.get(index));
            }
        }
    }

    public void decreaseStackSize(final int index, final int count) {
        final ItemStack stack = getStackInSlot(index);
        stack.shrink(count);
        onContentsChanged(index);
    }

    @NotNull
    @Override
    public ItemStack insertItem(final int slot, @NotNull final ItemStack stack, final boolean simulate) {
        return INPUT.equals(inventoryType) ? super.insertItem(slot, stack, simulate) : ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        return OUTPUT.equals(inventoryType) ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }
}
