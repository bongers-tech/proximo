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
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tech.bongers.proximo.Proximo;

@Mod.EventBusSubscriber(modid = Proximo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CreativeTabRegistry {

    private CreativeTabRegistry() {
        // No-args
    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Proximo.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PROXIMO_TAB = TABS.register("proximo_tab",
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
