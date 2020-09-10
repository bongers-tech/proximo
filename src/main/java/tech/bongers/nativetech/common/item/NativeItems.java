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
import tech.bongers.nativetech.common.util.NativeProperties;
import tech.bongers.nativetech.common.util.Reference;

public final class NativeItems {

    private NativeItems() {
        // No-args
    }

    public static final ItemGroup CREATIVE_TAB = new NativeTechItemGroup();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    /* ITEM BLOCKS */
    public static final RegistryObject<Item> COMPRESSED_REDSTONE_BLOCK = ITEMS.register(
            NativeProperties.COMPRESSED_REDSTONE_BLOCK,
            () -> new BlockItem(NativeBlocks.COMPRESSED_REDSTONE_BLOCK.get(), itemProperties())
    );
    public static final RegistryObject<Item> DOUBLE_COMPRESSED_REDSTONE_BLOCK = ITEMS.register(
            NativeProperties.DOUBLE_COMPRESSED_REDSTONE_BLOCK,
            () -> new BlockItem(NativeBlocks.DOUBLE_COMPRESSED_REDSTONE_BLOCK.get(), itemProperties())
    );

    public static final RegistryObject<Item> REDSTONE_FURNACE = ITEMS.register(
            NativeProperties.REDSTONE_FURNACE,
            () -> new BlockItem(NativeBlocks.REDSTONE_FURNACE_BLOCK.get(), itemProperties())
    );

    /* ITEMS */
    public static final RegistryObject<Item> HARDENED_STONE_SWORD = ITEMS.register(
            "hardened_stone_sword",
            () -> new SwordItem(ItemTier.IRON, 4, -2.4F, itemProperties())
    );

    public static Item.Properties itemProperties() {
        return new Item.Properties().group(CREATIVE_TAB);
    }
}
