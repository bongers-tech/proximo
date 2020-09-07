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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.tileentity.RedstoneFurnaceTileEntity;
import tech.bongers.nativetech.common.util.FunctionalIntReferenceHolder;

import java.util.Objects;

public class RedstoneFurnaceContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;
    private final RedstoneFurnaceTileEntity tileEntity;
    private final FunctionalIntReferenceHolder currentBurnTime;
    private final FunctionalIntReferenceHolder currentSmeltTime;

    //Client
    public RedstoneFurnaceContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(id, playerInventory, getTileEntity(playerInventory, data));
    }

    //Server
    public RedstoneFurnaceContainer(int id, final PlayerInventory inventory, final TileEntity tileEntity) {
        super(NativeContainer.REDSTONE_FURNACE_CONTAINER.get(), id);
        Objects.requireNonNull(tileEntity.getWorld());

        this.tileEntity = (RedstoneFurnaceTileEntity) tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.currentBurnTime = new FunctionalIntReferenceHolder(this.tileEntity::getCurrentBurnTime, this.tileEntity::setCurrentBurnTime);
        this.currentSmeltTime = new FunctionalIntReferenceHolder(this.tileEntity::getCurrentSmeltTime, this.tileEntity::setCurrentSmeltTime);
        this.trackInt(this.currentBurnTime);
        this.trackInt(this.currentSmeltTime);

        addBlockSlots();
        bindInventory(inventory);
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerEntity) {
        return isWithinUsableDistance(canInteractWithCallable, playerEntity, NativeBlocks.REDSTONE_FURNACE_BLOCK.get());
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

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled() {
        return currentSmeltTime.get() != 0
                ? currentSmeltTime.get() * 24 / RedstoneFurnaceTileEntity.MAX_SMELT_TIME
                : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        return currentBurnTime.get() != 0
                ? currentBurnTime.get() * 13 / RedstoneFurnaceTileEntity.MAX_BURN_TIME
                : 0;
    }

    private void bindInventory(final PlayerInventory inventory) {
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

    private void addBlockSlots() {
        addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 56, 17));
        addSlot(new SlotItemHandler(tileEntity.getInventory(), 1, 56, 53));
        addSlot(new SlotItemHandler(tileEntity.getInventory(), 2, 116, 35));
    }

    private boolean performMerge(final int slotIndex, final ItemStack stack) {
        int invBase = 3;
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }

    private static RedstoneFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPosition = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPosition instanceof RedstoneFurnaceTileEntity) {
            return (RedstoneFurnaceTileEntity) tileAtPosition;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPosition);
    }
}
