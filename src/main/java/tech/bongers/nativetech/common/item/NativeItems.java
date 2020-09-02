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
package tech.bongers.nativetech.common.item;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.item.group.NativeTechItemGroup;
import tech.bongers.nativetech.common.util.Reference;

public final class NativeItems {

    private NativeItems() {
        // No-args
    }

    public static final ItemGroup CREATIVE_TAB = new NativeTechItemGroup();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    // Item Blocks
    public static final RegistryObject<Item> HARDENED_STONE_ITEM = ITEMS.register(
            "hardened_stone",
            () -> new BlockItem(NativeBlocks.HARDENED_STONE.get(), itemProperties())
    );

    public static final RegistryObject<Item> REDSTONE_GENERATOR_ITEM = ITEMS.register(
            "redstone_generator",
            () -> new BlockItem(NativeBlocks.REDSTONE_GENERATOR.get(), itemProperties())
    );

    // Items
    public static final RegistryObject<Item> HARDENED_STONE_SWORD = ITEMS.register(
            "hardened_stone_sword",
            () -> new SwordItem(ItemTier.IRON, 4, -2.4F, itemProperties())
    );

    public static Item.Properties itemProperties() {
        return new Item.Properties().group(CREATIVE_TAB);
    }
}
