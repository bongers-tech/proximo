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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import tech.bongers.nativetech.common.container.RedstoneFurnaceContainer;
import tech.bongers.nativetech.common.handler.NativeItemHandler;

import javax.annotation.Nullable;
import java.util.Set;

import static tech.bongers.nativetech.common.util.NativeProperties.COMPACTOR;

public class CompactorTileEntity extends AbstractNativeTileEntity {

    private int process;

    public CompactorTileEntity() {
        super(NativeTileEntity.COMPACTOR_TILE_ENTITY.get(), new NativeItemHandler(2));
    }

    @Override
    protected String getItemName() {
        return COMPACTOR;
    }

    @Override
    public Container createMenu(final int id, final PlayerInventory playerInventory, final PlayerEntity playerEntity) {
        return new RedstoneFurnaceContainer(id, playerInventory, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (world != null && !world.isRemote) {
            final ICraftingRecipe recipes = getRecipe(getInventory().getStackInSlot(0));
            // DO STUFF
        }

        if (dirty) {
            markDirty();
        }
    }

    @Override
    public void read(final BlockState state, final CompoundNBT nbt) {
        super.read(state, nbt);
        process = nbt.getInt("Process");
    }

    @Override
    public CompoundNBT write(final CompoundNBT nbt) {
        super.write(nbt);
        nbt.putInt("Process", process);
        return nbt;
    }

    @Nullable
    private ICraftingRecipe getRecipe(final ItemStack stack) {
        if (stack != null) {
            final Set<IRecipe<?>> recipes = findRecipesForType(world, IRecipeType.CRAFTING);
            for (IRecipe<?> iRecipe : recipes) {
                ICraftingRecipe recipe = (ICraftingRecipe) iRecipe;
                return recipe;
                // FILTER RECIPES ON INPUT COMPACTING
            }
        }
        return null;
    }

    private void compact(@Nullable final IRecipe<?> recipe) {
        if (recipe != null) {
            //COMPACT INPUT ITEMS
        }
    }

    /* GETTERS */
    public int getProcess() {
        return process;
    }

    /* SETTERS */
    public void setProcess(int process) {
        this.process = process;
    }
}
