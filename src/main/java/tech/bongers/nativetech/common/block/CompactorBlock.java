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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import tech.bongers.nativetech.common.tileentity.NativeTileEntity;

import java.util.Random;

public class CompactorBlock extends AbstractNativeTileBlock {

    public CompactorBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIVE, true));
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return NativeTileEntity.COMPACTOR_TILE_ENTITY.get().create();
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected ActionResultType onTileBlockActivated(final World world, final BlockPos pos, final PlayerEntity playerEntity) {
        //if (!world.isRemote) {
            //final TileEntity tileEntity = world.getTileEntity(pos);
            //if (tileEntity instanceof CompactorTileEntity) {
            //    NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
            //    return ActionResultType.SUCCESS;
            //}
        //}
        return ActionResultType.PASS;
    }

    @Override
    public void animateTick(final BlockState stateIn, final World worldIn, final BlockPos pos, final Random rand) {
        //if (stateIn.get(ACTIVE)) {
        final double xPos = pos.getX();
        final double yPos = pos.getY() + 0.8D;
        final double zPos = pos.getZ();

        //if (rand.nextDouble() < 0.1D) {
        //    worldIn.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        //}

        final Direction direction = stateIn.get(FACING);
        final Direction.AxisDirection axisDirection = direction.getAxisDirection();
        final Direction.Axis axis = direction.getAxis();

        final double xOffset = axis == Direction.Axis.X ? direction.getXOffset() * 0.5D : direction.getZOffset() * 0.5D;
        final double zOffset = axis == Direction.Axis.Z ? direction.getZOffset() * 0.5D : direction.getXOffset() * 0.5D;
        //final double yOffset = rand.nextDouble() * 9.0D / 16.0D;

        final double xDest = axisDirection == Direction.AxisDirection.POSITIVE ? xPos + xOffset : xPos - xOffset;
        final double zDest = axisDirection == Direction.AxisDirection.POSITIVE ? zPos + zOffset : zPos - zOffset;
        worldIn.addParticle(ParticleTypes.FIREWORK, xDest, yPos, zDest, 0.D, 0.08D, 0.0D);
        //}
    }
}
