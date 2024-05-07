package tech.bongers.proximo.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.bongers.proximo.Proximo;
import tech.bongers.proximo.common.block.CompressedRedstoneBlock;
import tech.bongers.proximo.common.util.ProximoProperties;

public final class BlockRegistry {
    
    private BlockRegistry() {
        // No-args
    }
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Proximo.MOD_ID);
    
    public static final RegistryObject<Block> COMPRESSED_REDSTONE_BLOCK = BLOCKS.register(ProximoProperties.COMPRESSED_REDSTONE_BLOCK, CompressedRedstoneBlock::new);
}
