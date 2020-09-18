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
package tech.bongers.proximo.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.proximo.common.Proximo;
import tech.bongers.proximo.common.block.ProximoBlocks;

import static tech.bongers.proximo.common.util.ProximoProperties.PACKAGER;
import static tech.bongers.proximo.common.util.ProximoProperties.REDSTONE_FURNACE;

public final class ProximoTileEntity {

    private ProximoTileEntity() {
        // No-args
    }

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Proximo.MOD_ID);

    public static final RegistryObject<TileEntityType<RedstoneFurnaceTileEntity>> REDSTONE_FURNACE_TILE_ENTITY = TILE_ENTITIES.register(
            REDSTONE_FURNACE,
            () -> TileEntityType.Builder.create(RedstoneFurnaceTileEntity::new, ProximoBlocks.REDSTONE_FURNACE_BLOCK.get()).build(null)
    );
    public static final RegistryObject<TileEntityType<PackagerTileEntity>> PACKAGER_TILE_ENTITY = TILE_ENTITIES.register(
            PACKAGER,
            () -> TileEntityType.Builder.create(PackagerTileEntity::new, ProximoBlocks.PACKAGER_BLOCK.get()).build(null)
    );
}
