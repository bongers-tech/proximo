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
package tech.bongers.proximo.common.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import tech.bongers.proximo.common.block.ProximoBlocks;
import tech.bongers.proximo.common.gui.slot.ResultSlot;
import tech.bongers.proximo.common.tileentity.AbstractProximoTileEntity;
import tech.bongers.proximo.common.tileentity.PackagerTileEntity;
import tech.bongers.proximo.common.util.FunctionalIntReferenceHolder;

import java.util.Objects;

import static tech.bongers.proximo.common.tileentity.PackagerTileEntity.PROCESSING_TIME;

public class PackagerContainer extends AbstractProximoContainer<PackagerTileEntity> {

    private final FunctionalIntReferenceHolder process;

    //Client
    public PackagerContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(id, playerInventory, getTileEntityFromData(playerInventory, data));
    }

    //Server
    public PackagerContainer(int id, final PlayerInventory playerInventory, final AbstractProximoTileEntity tileEntity) {
        super(ProximoContainer.PACKAGER_CONTAINER.get(), id, playerInventory, tileEntity);
        Objects.requireNonNull(tileEntity.getWorld());

        this.process = new FunctionalIntReferenceHolder(getTileEntity()::getProcess, getTileEntity()::setProcess);
        this.trackInt(this.process);
    }

    @Override
    protected void addContainerSlots() {
        final int startX = 8;
        final int startY = 18;

        final int totalRows = 3;
        final int slotsInRow = 3;
        final int slotSize = 18; //slot = 16, border = 2

        // Input inventory
        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < slotsInRow; column++) {
                final int index = (row * slotsInRow) + column;
                final int xPosition = startX + column * slotSize;
                final int yPosition = startY + row * slotSize;
                addSlot(new SlotItemHandler(getTileEntity().getInventory(), index, xPosition, yPosition));
            }
        }

        // Output inventory
        final int indexOffset = 9;
        final int guiOffset = 108;
        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < slotsInRow; column++) {
                final int index = (row * slotsInRow) + column + indexOffset;
                final int xPosition = startX + column * slotSize + guiOffset;
                final int yPosition = startY + row * slotSize;
                addSlot(new ResultSlot(getTileEntity().getInventory(), index, xPosition, yPosition));
            }
        }
    }

    @Override
    protected Block getTargetBlock() {
        return ProximoBlocks.PACKAGER_BLOCK.get();
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessScaled() {
        return process.get() != 0
                ? process.get() * 24 / PROCESSING_TIME
                : 0;
    }

    protected boolean performMerge(final int slotIndex, final ItemStack stack) {
        int invBase = 18;
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }
}
