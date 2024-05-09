/*
 *     Copyright © 2024 bongers-tech
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
package tech.bongers.proximo.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.bongers.proximo.Proximo;

import static tech.bongers.proximo.common.util.ProximoProperties.PROXIMO_CREATIVE_TAB;

@EventBusSubscriber(modid = Proximo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CreativeTabRegistry {

    private CreativeTabRegistry() {
        // No-args
    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Proximo.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PROXIMO_TAB = TABS.register(PROXIMO_CREATIVE_TAB,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.proximo"))
                    .icon(ItemRegistry.EDIBLE_REDSTONE.get()::getDefaultInstance)
                    .displayItems((itemDisplayParameters, output) -> {
                        BlockRegistry.BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
                        ItemRegistry.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    @SubscribeEvent
    public static void buildContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(BlockRegistry.COMPRESSED_REDSTONE_BLOCK);
        }
    }
}
