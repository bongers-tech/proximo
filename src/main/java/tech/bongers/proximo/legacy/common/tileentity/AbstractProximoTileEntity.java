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
package tech.bongers.proximo.legacy.common.tileentity;

public abstract class AbstractProximoTileEntity /*extends TileEntity implements ITickableTileEntity, INamedContainerProvider */{

    /*private final ProximoItemHandler inventory;
    private LazyOptional<IItemHandler> handler = LazyOptional.empty();

    public AbstractProximoTileEntity(final TileEntityType<?> tileEntityTypeIn, final ProximoItemHandler inventory) {
        super(tileEntityTypeIn);
        this.inventory = inventory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + Proximo.MOD_ID + "." + getItemName());
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, final Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return getItemHandlerCapability().cast();
        }
        return super.getCapability(capability, side);
    }

    public ProximoItemHandler getInventory() {
        return inventory;
    }

    public <T> LazyOptional<T> getItemHandlerCapability() {
        if (!handler.isPresent()) {
            handler = LazyOptional.of(inventory::getManagedItemHandler);
        }
        return handler.cast();
    }

    protected abstract String getItemName();*/
}
