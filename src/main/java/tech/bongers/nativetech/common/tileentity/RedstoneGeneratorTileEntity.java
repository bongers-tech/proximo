package tech.bongers.nativetech.common.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.BlastFurnaceContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RedstoneGeneratorTileEntity extends AbstractFurnaceTileEntity {

    public RedstoneGeneratorTileEntity() {
        super(NativeTileEntity.REDSTONE_GENERATOR_TILE_ENTITY.get(), IRecipeType.BLASTING);
    }

    @Override
    protected int getBurnTime(final ItemStack fuel) {
        return super.getBurnTime(fuel) / 4;
    }

    @Override
    protected int getCookTime() {
        return super.getCookTime() / 2;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.nativetech.redstone_generator");
    }

    protected Container createMenu(final int id, final PlayerInventory player) {
        return new BlastFurnaceContainer(id, player, this, this.furnaceData);
    }
}
