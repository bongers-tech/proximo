/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of NativeTech.
 *
 *     NativeTech is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     NativeTech is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with NativeTech. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.nativetech.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.item.NativeItems;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum FuelProperties {

    REDSTONE(NativeBlocks.REDSTONE_FURNACE_BLOCK.get(), Items.REDSTONE, 50, 50),
    REDSTONE_BLOCK(NativeBlocks.REDSTONE_FURNACE_BLOCK.get(), Items.REDSTONE_BLOCK, 50, 400),
    COMPRESSED_REDSTONE_BLOCK(NativeBlocks.REDSTONE_FURNACE_BLOCK.get(), NativeItems.COMPRESSED_REDSTONE_BLOCK.get(), 50, 800),
    DOUBLE_COMPRESSED_REDSTONE_BLOCK(NativeBlocks.REDSTONE_FURNACE_BLOCK.get(), NativeItems.DOUBLE_COMPRESSED_REDSTONE_BLOCK.get(), 50, 1600);

    private final Block block;
    private final Item fuel;
    private final int smeltTime;
    private final int burnTime;

    FuelProperties(final Block block, final Item fuel, final int smeltTime, final int burnTime) {
        this.block = block;
        this.fuel = fuel;
        this.smeltTime = smeltTime;
        this.burnTime = burnTime;
    }

    public Block getBlock() {
        return block;
    }

    public Item getFuel() {
        return fuel;
    }

    public int getSmeltTime() {
        return smeltTime;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public static List<FuelProperties> forBlock(final Block block) {
        return Arrays.stream(FuelProperties.values())
                .filter(p -> p.getBlock() == block)
                .collect(Collectors.toList());
    }

    public static int getSmeltTimeForFuel(final Item fuel) {
        return forFuel(fuel)
                .map(FuelProperties::getSmeltTime)
                .orElse(0);
    }

    public static int getBurnTimeForFuel(final Item fuel) {
        return forFuel(fuel)
                .map(FuelProperties::getBurnTime)
                .orElse(0);
    }

    public static boolean isFuel(final Item fuel) {
        return Arrays.stream(FuelProperties.values())
                .anyMatch(p -> p.getFuel() == fuel);
    }

    private static Optional<FuelProperties> forFuel(final Item fuel) {
        return Arrays.stream(FuelProperties.values())
                .filter(p -> p.getFuel() == fuel)
                .findFirst();
    }
}
