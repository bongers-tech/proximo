package tech.bongers.nativetech.common.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.util.Reference;

public final class NativeBlocks {

    private NativeBlocks() {
        // No-args
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    public static final RegistryObject<Block> HARDENED_STONE = BLOCKS.register("hardened_stone", HardenedStoneBlock::new);
}
