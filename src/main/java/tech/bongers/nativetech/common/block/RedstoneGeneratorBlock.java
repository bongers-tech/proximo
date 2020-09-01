package tech.bongers.nativetech.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import tech.bongers.nativetech.common.tileentity.RedstoneGeneratorTileEntity;

import java.util.Random;

public class RedstoneGeneratorBlock extends AbstractFurnaceBlock {

    protected RedstoneGeneratorBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F));
    }

    @Override
    protected void interactWith(final World worldIn, final BlockPos pos, final PlayerEntity player) {
        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof RedstoneGeneratorTileEntity) {
            player.openContainer((INamedContainerProvider) tileEntity);
            player.addStat(Stats.INTERACT_WITH_BLAST_FURNACE);
        }
    }

    @Override
    public TileEntity createNewTileEntity(final IBlockReader worldIn) {
        return new RedstoneGeneratorTileEntity();
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
            final Direction.Axis directionAxis = direction.getAxis();

            final double xOffset = directionAxis == Direction.Axis.X ? direction.getXOffset() * 0.5D : direction.getZOffset() * 0.5D;
            final double yOffset = rand.nextDouble() * 9.0D / 16.0D;
            final double zOffset = directionAxis == Direction.Axis.Z ? direction.getZOffset() * 0.5D : direction.getXOffset() * 0.5D;

            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, xPos + xOffset, yPos + yOffset, zPos + zOffset, 0.0D, 0.0D, 0.0D);
        }
    }
}
