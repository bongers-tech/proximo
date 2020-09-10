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
package tech.bongers.nativetech.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import tech.bongers.nativetech.common.tileentity.AbstractNativeTileEntity;

import static tech.bongers.nativetech.common.util.NativeUtils.dropItemStackIntoWorld;

public abstract class AbstractNativeTileBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public AbstractNativeTileBlock(final Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, true));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockHarvested(final World world, final BlockPos pos, final BlockState state, final PlayerEntity playerEntity) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof AbstractNativeTileEntity) {
                final AbstractNativeTileEntity te = (AbstractNativeTileEntity) tileEntity;
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

    protected abstract ActionResultType onTileBlockActivated(final World world, final BlockPos pos, final PlayerEntity playerEntity);
}
