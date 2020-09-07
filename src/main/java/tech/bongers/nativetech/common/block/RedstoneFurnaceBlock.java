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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tech.bongers.nativetech.common.tileentity.NativeTileEntity;
import tech.bongers.nativetech.common.tileentity.RedstoneFurnaceTileEntity;

import java.util.Random;

public class RedstoneFurnaceBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public RedstoneFurnaceBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return NativeTileEntity.REDSTONE_FURNACE_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, LIT);
    }

    @Override
    public int getLightValue(final BlockState state, final IBlockReader world, final BlockPos pos) {
        return state.get(LIT) ? 12 : 0;
    }

    @Override
    public BlockState getStateForPlacement(final BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onEntityWalk(final World world, final BlockPos pos, final Entity entity) {
        if (world.isBlockPowered(pos)
                && !entity.isImmuneToFire()
                && entity instanceof LivingEntity
                && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)){
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
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
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof RedstoneFurnaceTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }


    @Override
    public void animateTick(final BlockState stateIn, final World worldIn, final BlockPos pos, final Random rand) {
        if (stateIn.get(LIT)) {
            final double xPos = pos.getX();
            final double yPos = pos.getY() + 0.8D;
            final double zPos = pos.getZ();

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            final Direction direction = stateIn.get(FACING);
            final Direction.AxisDirection axisDirection = direction.getAxisDirection();
            final Direction.Axis axis = direction.getAxis();

            final double xOffset = axis == Direction.Axis.X ? direction.getXOffset() * 0.5D : direction.getZOffset() * 0.5D;
            final double zOffset = axis == Direction.Axis.Z ? direction.getZOffset() * 0.5D : direction.getXOffset() * 0.5D;
            final double yOffset = rand.nextDouble() * 9.0D / 16.0D;

            final double xDest = axisDirection == Direction.AxisDirection.POSITIVE ? xPos + xOffset : xPos - xOffset;
            final double zDest = axisDirection == Direction.AxisDirection.POSITIVE ? zPos + zOffset : zPos - zOffset;
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, xDest, yPos + yOffset, zDest, 0.0D, 0.0D, 0.0D);
        }
    }
}
