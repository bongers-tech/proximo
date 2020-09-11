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
package tech.bongers.nativetech.common.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import tech.bongers.nativetech.common.tileentity.AbstractNativeTileEntity;

import java.util.Objects;

public abstract class AbstractNativeContainer<T> extends Container {

    private final AbstractNativeTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public AbstractNativeContainer(final ContainerType<?> type, final int id, final PlayerInventory playerInventory, final AbstractNativeTileEntity tileEntity) {
        super(type, id);
        Objects.requireNonNull(tileEntity.getWorld());

        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        addContainerSlots();
        bindPlayerInventory(playerInventory);
    }

    @SuppressWarnings("unchecked")
    public T getTileEntity() {
        return (T) tileEntity;
    }

    public void bindPlayerInventory(final PlayerInventory inventory) {
        final int startX = 8;
        final int startY = 84;

        final int totalRows = 3;
        final int slotsInRow = 9;
        final int slotSize = 18; //slot = 16, border = 2

        // Player inventory. 36 slots
        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < slotsInRow; column++) {
                final int index = (row * slotsInRow) + column + slotsInRow;
                final int xPosition = startX + column * slotSize;
                final int yPosition = startY + row * slotSize;
                addSlot(new Slot(inventory, index, xPosition, yPosition));
            }
        }

        //Player hotbar
        final int hotBarOffset = 4;
        for (int column = 0; column < slotsInRow; column++) {
            final int xPosition = startX + column * slotSize;
            final int yPosition = startY + (slotSize * totalRows) + hotBarOffset;
            addSlot(new Slot(inventory, column, xPosition, yPosition));
        }
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerEntity) {
        return isWithinUsableDistance(canInteractWithCallable, playerEntity, getTargetBlock());
    }

    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int slotIndex) {
        ItemStack stack = ItemStack.EMPTY;
        final Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            final ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(slotIndex, stackInSlot)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stackInSlot, stack);

            final ItemStack itemStack = stackInSlot.getCount() <= 0 ? ItemStack.EMPTY : stackInSlot;
            slot.putStack(itemStack);

            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
        }
        return stack;
    }

    protected static AbstractNativeTileEntity getTileEntityFromData(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPosition = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPosition instanceof AbstractNativeTileEntity) {
            return (AbstractNativeTileEntity) tileAtPosition;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPosition);
    }

    protected abstract void addContainerSlots();

    protected abstract Block getTargetBlock();

    protected abstract boolean performMerge(final int slotIndex, final ItemStack stackInSlot);

}
