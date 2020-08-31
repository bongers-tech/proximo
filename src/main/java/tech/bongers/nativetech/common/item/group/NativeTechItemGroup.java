package tech.bongers.nativetech.common.item.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tech.bongers.nativetech.common.item.NativeItems;
import tech.bongers.nativetech.common.util.Reference;

public class NativeTechItemGroup extends ItemGroup {

    public NativeTechItemGroup() {
        super(Reference.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(NativeItems.STONE_DIRT_ITEM.get());
    }
}
