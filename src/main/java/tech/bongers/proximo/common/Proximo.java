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
package tech.bongers.proximo.common;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.bongers.proximo.client.screen.PackagerScreen;
import tech.bongers.proximo.client.screen.RedstoneFurnaceScreen;
import tech.bongers.proximo.common.block.ProximoBlocks;
import tech.bongers.proximo.common.container.ProximoContainer;
import tech.bongers.proximo.common.item.ProximoItems;
import tech.bongers.proximo.common.tileentity.ProximoTileEntity;

import java.util.stream.Collectors;

@Mod(Proximo.MOD_ID)
public class Proximo {

    public static final String MOD_ID = "proximo";
    private static final Logger LOGGER = LogManager.getLogger();

    public Proximo() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ProximoBlocks.BLOCKS.register(eventBus);
        ProximoItems.ITEMS.register(eventBus);
        ProximoTileEntity.TILE_ENTITIES.register(eventBus);
        ProximoContainer.CONTAINERS.register(eventBus);

        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ProximoContainer.REDSTONE_FURNACE_CONTAINER.get(), RedstoneFurnaceScreen::new);
        ScreenManager.registerFactory(ProximoContainer.PACKAGER_CONTAINER.get(), PackagerScreen::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(Proximo.MOD_ID, "helloworld", () -> "Hello world");
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        LOGGER.info("Server starting");
    }
}
