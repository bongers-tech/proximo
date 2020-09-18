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
package tech.bongers.proximo.common.item.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tech.bongers.proximo.common.Proximo;
import tech.bongers.proximo.common.item.ProximoItems;

public class ProximoTechItemGroup extends ItemGroup {

    public ProximoTechItemGroup() {
        super(Proximo.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ProximoItems.REDSTONE_FURNACE.get());
    }
}
