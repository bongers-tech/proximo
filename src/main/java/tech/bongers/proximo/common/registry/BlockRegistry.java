package tech.bongers.proximo.common.registry;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.bongers.proximo.Proximo;
import tech.bongers.proximo.common.block.CompressedRedstoneBlock;
import tech.bongers.proximo.common.util.ProximoProperties;

public final class BlockRegistry {

    private BlockRegistry() {
        // No-args
    }

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Proximo.MOD_ID);

    public static final DeferredBlock<Block> COMPRESSED_REDSTONE_BLOCK = BLOCKS.register(ProximoProperties.COMPRESSED_REDSTONE_BLOCK, CompressedRedstoneBlock::new);
}
