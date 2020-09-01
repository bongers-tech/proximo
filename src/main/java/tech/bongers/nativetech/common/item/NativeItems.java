package tech.bongers.nativetech.common.item;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.item.group.NativeTechItemGroup;
import tech.bongers.nativetech.common.util.Reference;

public final class NativeItems {

    private NativeItems() {
        // No-args
    }

    public static final ItemGroup CREATIVE_TAB = new NativeTechItemGroup();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    // Item Blocks
    public static final RegistryObject<Item> HARDENED_STONE_ITEM = ITEMS.register(
            "hardened_stone",
            () -> new BlockItem(NativeBlocks.HARDENED_STONE.get(), NativeItems.itemProperties())
    );

    // Items
    public static final RegistryObject<Item> HARDENED_STONE_SWORD = ITEMS.register(
            "hardened_stone_sword",
            () -> new SwordItem(ItemTier.IRON, 4, -2.4F, itemProperties())
    );

    public static Item.Properties itemProperties() {
        return new Item.Properties().group(CREATIVE_TAB);
    }
}
