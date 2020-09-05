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
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tech.bongers.nativetech.common.gui.slot.RedstoneFuelSlot;
import tech.bongers.nativetech.common.tileentity.NativeTileEntity;
import tech.bongers.nativetech.common.tileentity.RedstoneGeneratorTileEntity;

public class RedstoneGeneratorContainer extends Container {

    private final RedstoneGeneratorTileEntity tileEntity;
    // private final IIntArray furnaceData;

    public static RedstoneGeneratorContainer create(int id, PlayerInventory inventory, PacketBuffer packetBuffer) {
        return new RedstoneGeneratorContainer(id, inventory, NativeTileEntity.REDSTONE_GENERATOR_TILE_ENTITY.get().create(), new IntArray(4));
    }

    public RedstoneGeneratorContainer(int id, final PlayerInventory inventory, final TileEntity tileEntity, final IIntArray furnaceData) {
        super(NativeContainer.REDSTONE_GENERATOR_CONTAINER.get(), id);
        this.tileEntity = (RedstoneGeneratorTileEntity) tileEntity;

        addSlots(inventory);
        bindInventory(inventory);
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

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // Determine what conditions should be added here
        return true;
    }

    private void addSlots(final PlayerInventory inventory) {
        addSlot(new Slot(inventory, 36, 56, 17));
        addSlot(new RedstoneFuelSlot(inventory, 37, 56, 53));
        addSlot(new FurnaceResultSlot(inventory.player, inventory, 38, 116, 35));
    }

    private void bindInventory(final PlayerInventory inventory) {
        // Player inventory. Max index = 35 (36 slots)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                final int index = j + i * 9 + 9;
                final int xPosition = 8 + j * 18;
                final int yPosition = 84 + i * 18;
                addSlot(new Slot(inventory, index, xPosition, yPosition));
            }
        }

        //Player hotbar
        for (int i = 0; i < 9; i++) {
            final int xPosition = 8 + i * 18;
            final int yPosition = 142;
            addSlot(new Slot(inventory, i, xPosition, yPosition));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = tileEntity.getActualCookTime();
        int j = tileEntity.getCookTime();
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        int i = tileEntity.getActualCookTime();
        return tileEntity.getBurnTime(null) / (i * 2);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        final int burnTime = tileEntity.getBurnTime(null);
        return burnTime > 0;
    }

    private boolean performMerge(final int slotIndex, final ItemStack stack) {
        int invBase = 3;
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return mergeItemStack(stack, invBase, invFull, true);
        }
        return mergeItemStack(stack, 0, invBase, false);
    }
}
