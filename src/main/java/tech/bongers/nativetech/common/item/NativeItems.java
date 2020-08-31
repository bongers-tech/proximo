package tech.bongers.nativetech.common.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
    public static final RegistryObject<Item> STONE_DIRT_ITEM = ITEMS.register(
            "stone_dirt",
            () -> new BlockItem(NativeBlocks.STONE_DIRT.get(), NativeItems.itemProperties())
    );

    public static Item.Properties itemProperties() {
        return new Item.Properties().group(CREATIVE_TAB);
    }
}
