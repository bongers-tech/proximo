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

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.NativeTech;
import tech.bongers.nativetech.common.block.NativeBlocks;

import static tech.bongers.nativetech.common.util.NativeProperties.COMPACTOR;
import static tech.bongers.nativetech.common.util.NativeProperties.REDSTONE_FURNACE;

public final class NativeTileEntity {

    private NativeTileEntity() {
        // No-args
    }

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NativeTech.MOD_ID);

    public static final RegistryObject<TileEntityType<RedstoneFurnaceTileEntity>> REDSTONE_FURNACE_TILE_ENTITY = TILE_ENTITIES.register(
            REDSTONE_FURNACE,
            () -> TileEntityType.Builder.create(RedstoneFurnaceTileEntity::new, NativeBlocks.REDSTONE_FURNACE_BLOCK.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<CompactorTileEntity>> COMPACTOR_TILE_ENTITY = TILE_ENTITIES.register(
            COMPACTOR,
            () -> TileEntityType.Builder.create(CompactorTileEntity::new, NativeBlocks.COMPACTOR_BLOCK.get()).build(null)
    );
}
