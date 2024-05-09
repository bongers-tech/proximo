/*
 *     Copyright © 2024 bongers-tech
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
package tech.bongers.proximo.common.registry;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.bongers.proximo.Proximo;
import tech.bongers.proximo.common.block.item.CompressedRedstoneBlockItem;
import tech.bongers.proximo.common.util.ProximoProperties;

import java.util.function.Supplier;

public final class BlockItemRegistry {

    private BlockItemRegistry() {
        // No args
    }

    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Proximo.MOD_ID);

    public static final Supplier<BlockItem> COMPRESSED_REDSTONE_BLOCK_ITEM = BLOCK_ITEMS.register(ProximoProperties.COMPRESSED_REDSTONE_BLOCK, CompressedRedstoneBlockItem::new);

}
