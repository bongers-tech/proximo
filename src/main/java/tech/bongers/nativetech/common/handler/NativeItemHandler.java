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

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class NativeItemHandler extends ItemStackHandler {

    public NativeItemHandler(final int size) {
        super(size);
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
}
