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

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import tech.bongers.nativetech.common.container.RedstoneGeneratorContainer;

public class RedstoneGeneratorTileEntity extends AbstractFurnaceTileEntity {

    private int cookTime;

    public RedstoneGeneratorTileEntity() {
        super(NativeTileEntity.REDSTONE_GENERATOR_TILE_ENTITY.get(), IRecipeType.BLASTING);
    }

    @Override
    public int getBurnTime(final ItemStack fuel) {
        return 1600 / 4;
    }

    @Override
    public int getCookTime() {
        return 200 / 2;
    }

    public int getActualCookTime() {
        //Testing UI. Should be moved to tick().
        ++this.cookTime;
        if (this.cookTime == getCookTime()) {
            this.cookTime = 0;
        }
        return this.cookTime;
    }

    @Override
    protected boolean canSmelt(final IRecipe<?> recipeIn) {
        //Testing UI. Always true for now
        return true;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.nativetech.redstone_generator");
    }

    protected Container createMenu(final int id, final PlayerInventory player) {
        return new RedstoneGeneratorContainer(id, player, this, this.furnaceData);
    }
}
