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
package tech.bongers.proximo.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tech.bongers.proximo.common.tileentity.PackagerTileEntity;
import tech.bongers.proximo.common.tileentity.ProximoTileEntity;
import tech.bongers.proximo.common.util.ProximoProperties;

public class PackagerBlock extends AbstractProximoTileBlock {

    public PackagerBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return ProximoTileEntity.PACKAGER_TILE_ENTITY.get().create();
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(ProximoProperties.FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected ActionResultType onTileBlockActivated(final World world, final BlockPos pos, final PlayerEntity playerEntity) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof PackagerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }
}