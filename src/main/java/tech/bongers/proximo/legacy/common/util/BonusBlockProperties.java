/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of Proximo.
 *
 *     Proximo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Proximo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Proximo. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.proximo.legacy.common.util;

import tech.bongers.proximo.legacy.common.block.ProximoBlocks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BonusBlockProperties {

    // FURNACE_REDSTONE(ProximoBlocks.REDSTONE_FURNACE_BLOCK.get(), Blocks.REDSTONE_BLOCK, 1),
    // FURNACE_COMPRESSED_REDSTONE(ProximoBlocks.REDSTONE_FURNACE_BLOCK.get(), ProximoBlocks.COMPRESSED_REDSTONE_BLOCK.get(), 4),
    // FURNACE_DOUBLE_COMPRESSED_REDSTONE(ProximoBlocks.REDSTONE_FURNACE_BLOCK.get(), ProximoBlocks.DOUBLE_COMPRESSED_REDSTONE_BLOCK.get(), 16);

   /* private final Block block;
    private final Block bonusBlock;
    private final int bonus;

    BonusBlockProperties(final Block block, final Block bonusBlock, final int bonus) {
        this.block = block;
        this.bonusBlock = bonusBlock;
        this.bonus = bonus;
    }

    public Block getBlock() {
        return block;
    }

    public Block getBonusBlock() {
        return bonusBlock;
    }

    public int getBonus() {
        return bonus;
    }

    public static List<BonusBlockProperties> forBlock(final Block block) {
        return Arrays.stream(BonusBlockProperties.values())
                .filter(p -> p.getBlock() == block)
                .sorted((p1, p2) -> Integer.compare(p2.getBonus(), p1.getBonus()))
                .collect(Collectors.toList());
    }*/
}
