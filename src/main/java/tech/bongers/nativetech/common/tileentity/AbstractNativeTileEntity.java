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
package tech.bongers.nativetech.common.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import tech.bongers.nativetech.common.NativeTech;
import tech.bongers.nativetech.common.handler.NativeItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractNativeTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private final NativeItemHandler inventory;

    public AbstractNativeTileEntity(final TileEntityType<?> tileEntityTypeIn, final NativeItemHandler inventory) {
        super(tileEntityTypeIn);
        this.inventory = inventory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + NativeTech.MOD_ID + "." + getItemName());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        NonNullList<ItemStack> inv = NonNullList.withSize(getInventory().getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, inv);
        getInventory().setNonNullList(inv);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        ItemStackHelper.saveAllItems(nbt, getInventory().toNonNullList());
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, final Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, LazyOptional.of(this::getInventory));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        ModelDataManager.requestModelDataRefresh(this);
        read(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    public NativeItemHandler getInventory() {
        return inventory;
    }

    protected abstract String getItemName();
}
