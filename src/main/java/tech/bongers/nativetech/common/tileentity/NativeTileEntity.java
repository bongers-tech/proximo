package tech.bongers.nativetech.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.util.Reference;

public final class NativeTileEntity {

    private NativeTileEntity() {
        // No-args
    }

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<TileEntityType<RedstoneGeneratorTileEntity>> REDSTONE_GENERATOR_TILE_ENTITY = TILE_ENTITIES.register(
            "redstone_generator",
            () -> TileEntityType.Builder.create(RedstoneGeneratorTileEntity::new, NativeBlocks.REDSTONE_GENERATOR.get()).build(null)
    );
}
