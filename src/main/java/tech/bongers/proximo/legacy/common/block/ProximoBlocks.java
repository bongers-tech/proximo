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
package tech.bongers.proximo.legacy.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.proximo.Proximo;

public final class ProximoBlocks {

    private ProximoBlocks() {
        // No-args
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Proximo.MOD_ID);

    /* BLOCKS */
    //public static final RegistryObject<Block> COMPRESSED_REDSTONE_BLOCK = BLOCKS.register(ProximoProperties.COMPRESSED_REDSTONE_BLOCK, CompressedRedstoneBlock::new);
    //public static final RegistryObject<Block> DOUBLE_COMPRESSED_REDSTONE_BLOCK = BLOCKS.register(ProximoProperties.DOUBLE_COMPRESSED_REDSTONE_BLOCK, DoubleCompressedRedstoneBlock::new);

    /* MACHINES */
    //public static final RegistryObject<Block> REDSTONE_FURNACE_BLOCK = BLOCKS.register(REDSTONE_FURNACE, RedstoneFurnaceBlock::new);
    //public static final RegistryObject<Block> PACKAGER_BLOCK = BLOCKS.register(PACKAGER, PackagerBlock::new);
}
