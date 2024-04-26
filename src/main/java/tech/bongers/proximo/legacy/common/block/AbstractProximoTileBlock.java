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

public abstract class AbstractProximoTileBlock extends Block {

    public AbstractProximoTileBlock(final Properties properties) {
        super(properties);
        /*this.withPropertiesOf(getBaseState()
                .with(ProximoProperties.FACING, Direction.NORTH)
                .with(ProximoProperties.ACTIVE, false));*/
    }

   /* @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, ACTIVE);
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockHarvested(final World world, final BlockPos pos, final BlockState state, final PlayerEntity playerEntity) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof AbstractProximoTileEntity) {
                final AbstractProximoTileEntity te = (AbstractProximoTileEntity) tileEntity;
                te.getInventory().toNonNullList().forEach(itemStack -> {
                    dropItemStackIntoWorld(itemStack, world, pos);
                });
            }
        }
        super.onBlockHarvested(world, pos, state, playerEntity);
    }

    @Override
    @SuppressWarnings("deprecation") //Overriding is fine
    public ActionResultType onBlockActivated(
            final BlockState state,
            final World world,
            final BlockPos pos,
            final PlayerEntity playerEntity,
            final Hand hand,
            final BlockRayTraceResult result) {
        return onTileBlockActivated(world, pos, playerEntity);
    }

    protected abstract ActionResultType onTileBlockActivated(final World world, final BlockPos pos, final PlayerEntity playerEntity);*/
}
