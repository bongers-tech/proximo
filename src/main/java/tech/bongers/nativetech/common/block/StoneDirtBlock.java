package tech.bongers.nativetech.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class StoneDirtBlock extends Block {

    public StoneDirtBlock() {
        super(AbstractBlock.Properties
                .create(Material.ROCK, MaterialColor.DIRT)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.STONE)
        );
    }
}
