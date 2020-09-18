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
package tech.bongers.proximo.common.item.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import tech.bongers.proximo.common.util.FuelProperties;

import javax.annotation.Nonnull;

public class ManagedItemHandler extends ItemStackHandler {

    private final ProximoItemHandler inventory;
    private final int inputSize;
    private final int outputSize;
    private int fuelSlot = -1;

    public ManagedItemHandler(final ProximoItemHandler inventory, final int inputSize, final int outputSize) {
        super(inputSize + outputSize);
        this.inventory = inventory;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
    }

    public void setSlotAsFuelSlot(final int fuelSlot) {
        this.fuelSlot = fuelSlot;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        if (slot < 0 || slot >= inputSize) {
            return stack;
        }

        if (fuelSlot >= 0 && (isFuelItemInRegularSlot(slot, stack) || isRegularItemInFuelSlot(slot, stack))) {
            return stack;
        }

        return inventory.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        if (slot < inputSize || slot >= inputSize + outputSize) {
            return ItemStack.EMPTY;
        }
        return inventory.extractItem(slot, amount, simulate);
    }

    private boolean isFuelItemInRegularSlot(final int slot, final ItemStack itemStack) {
        return FuelProperties.isFuel(itemStack.getItem()) && slot != fuelSlot;
    }

    private boolean isRegularItemInFuelSlot(final int slot, final ItemStack itemStack) {
        return !FuelProperties.isFuel(itemStack.getItem()) && slot == fuelSlot;
    }
}
