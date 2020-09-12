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
package tech.bongers.nativetech.common.item.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tech.bongers.nativetech.common.NativeTech;
import tech.bongers.nativetech.common.item.NativeItems;

public class NativeTechItemGroup extends ItemGroup {

    public NativeTechItemGroup() {
        super(NativeTech.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(NativeItems.REDSTONE_FURNACE.get());
    }
}
