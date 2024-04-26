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

public class PackagerBlock/* extends AbstractProximoTileBlock*/ {

   /* public PackagerBlock() {
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
    }*/
}
