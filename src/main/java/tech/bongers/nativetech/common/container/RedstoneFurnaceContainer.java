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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.gui.slot.RedstoneFuelSlot;
import tech.bongers.nativetech.common.gui.slot.ResultSlot;
import tech.bongers.nativetech.common.tileentity.AbstractNativeTileEntity;
import tech.bongers.nativetech.common.tileentity.RedstoneFurnaceTileEntity;
import tech.bongers.nativetech.common.util.FuelProperties;
import tech.bongers.nativetech.common.util.FunctionalIntReferenceHolder;

import java.util.Objects;

public class RedstoneFurnaceContainer extends AbstractNativeContainer<RedstoneFurnaceTileEntity> {

    private final FunctionalIntReferenceHolder currentBurnTime;
    private final FunctionalIntReferenceHolder currentSmeltTime;
    private final FunctionalIntReferenceHolder maxBurnTime;
    private final FunctionalIntReferenceHolder maxSmeltTime;

    //Client
    public RedstoneFurnaceContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(id, playerInventory, getTileEntityFromData(playerInventory, data));
    }

    //Server
    public RedstoneFurnaceContainer(int id, final PlayerInventory playerInventory, final AbstractNativeTileEntity tileEntity) {
        super(NativeContainer.REDSTONE_FURNACE_CONTAINER.get(), id, playerInventory, tileEntity);
        Objects.requireNonNull(tileEntity.getWorld());

        this.currentBurnTime = new FunctionalIntReferenceHolder(getTileEntity()::getCurrentBurnTime, getTileEntity()::setCurrentBurnTime);
        this.currentSmeltTime = new FunctionalIntReferenceHolder(getTileEntity()::getCurrentSmeltTime, getTileEntity()::setCurrentSmeltTime);
        this.maxBurnTime = new FunctionalIntReferenceHolder(getTileEntity()::getMaxBurnTime, getTileEntity()::setMaxBurnTime);
        this.maxSmeltTime = new FunctionalIntReferenceHolder(getTileEntity()::getMaxSmeltTime, getTileEntity()::setMaxSmeltTime);

        this.trackInt(this.currentBurnTime);
        this.trackInt(this.currentSmeltTime);
        this.trackInt(this.maxBurnTime);
        this.trackInt(this.maxSmeltTime);
    }

    @Override
    protected void addContainerSlots() {
        addSlot(new SlotItemHandler(getTileEntity().getInventory(), 0, 56, 17));
        addSlot(new RedstoneFuelSlot(getTileEntity().getInventory(), 1, 56, 53));
        addSlot(new ResultSlot(getTileEntity().getInventory(), 2, 116, 35));
    }

    @Override
    protected Block getTargetBlock() {
        return NativeBlocks.REDSTONE_FURNACE_BLOCK.get();
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled() {
        return currentSmeltTime.get() != 0 && maxSmeltTime.get() != 0
                ? currentSmeltTime.get() * 24 / maxSmeltTime.get()
                : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        return currentBurnTime.get() != 0 && maxBurnTime.get() != 0
                ? currentBurnTime.get() * 13 / maxBurnTime.get()
                : 0;
    }

    protected boolean performMerge(final int slotIndex, final ItemStack stack) {
        int invBase = 3;
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        } else if (FuelProperties.isFuel(stack.getItem())) {
            return mergeItemStack(stack, 1, 2, false);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }
}
