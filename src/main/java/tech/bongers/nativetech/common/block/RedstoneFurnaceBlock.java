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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tech.bongers.nativetech.common.tileentity.NativeTileEntity;
import tech.bongers.nativetech.common.tileentity.RedstoneFurnaceTileEntity;
import tech.bongers.nativetech.common.util.NativeProperties;

import java.util.Random;

import static tech.bongers.nativetech.common.util.NativeUtils.dropItemStackIntoWorld;

public class RedstoneFurnaceBlock extends AbstractNativeTileBlock {

    public RedstoneFurnaceBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return NativeTileEntity.REDSTONE_FURNACE_TILE_ENTITY.get().create();
    }

    @Override
    public int getLightValue(final BlockState state, final IBlockReader world, final BlockPos pos) {
        return state.get(NativeProperties.ACTIVE) ? 12 : 0;
    }

    @Override
    public void onEntityWalk(final World world, final BlockPos pos, final Entity entity) {
        if (world.isBlockPowered(pos)
                && !entity.isImmuneToFire()
                && entity instanceof LivingEntity
                && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
    }

    @Override
    protected ActionResultType onTileBlockActivated(World world, BlockPos pos, PlayerEntity playerEntity) {
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
    public void onBlockHarvested(final World world, final BlockPos pos, final BlockState state, final PlayerEntity playerEntity) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof RedstoneFurnaceTileEntity) {
                final RedstoneFurnaceTileEntity te = (RedstoneFurnaceTileEntity) tileEntity;
                te.getInventory().toNonNullList().forEach(itemStack -> {
                    dropItemStackIntoWorld(itemStack, world, pos);
                });
            }
        }
        super.onBlockHarvested(world, pos, state, playerEntity);
    }

    @Override
    public void animateTick(final BlockState stateIn, final World world, final BlockPos pos, final Random rand) {
        if (stateIn.get(NativeProperties.ACTIVE)) {
            final double xPos = pos.getX();
            final double yPos = pos.getY() + 0.8D;
            final double zPos = pos.getZ();

            if (rand.nextDouble() < 0.1D) {
                world.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            final Direction direction = stateIn.get(NativeProperties.FACING);
            final Direction.AxisDirection axisDirection = direction.getAxisDirection();
            final Direction.Axis axis = direction.getAxis();

            final double xOffset = axis == Direction.Axis.X ? direction.getXOffset() * 0.5D : direction.getZOffset() * 0.5D;
            final double zOffset = axis == Direction.Axis.Z ? direction.getZOffset() * 0.5D : direction.getXOffset() * 0.5D;
            final double yOffset = rand.nextDouble() * 9.0D / 16.0D;

            final double xDest = axisDirection == Direction.AxisDirection.POSITIVE ? xPos + xOffset : xPos - xOffset;
            final double zDest = axisDirection == Direction.AxisDirection.POSITIVE ? zPos + zOffset : zPos - zOffset;
            world.addParticle(ParticleTypes.LARGE_SMOKE, xDest, yPos + yOffset, zDest, 0.0D, 0.0D, 0.0D);
        }
    }
}
