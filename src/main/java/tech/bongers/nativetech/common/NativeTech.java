package tech.bongers.nativetech.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.bongers.nativetech.common.block.NativeBlocks;
import tech.bongers.nativetech.common.item.NativeItems;
import tech.bongers.nativetech.common.tileentity.NativeTileEntity;
import tech.bongers.nativetech.common.util.Reference;

import java.util.stream.Collectors;

@Mod(Reference.MOD_ID)
public class NativeTech {

    private static final Logger LOGGER = LogManager.getLogger();

    public NativeTech() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        NativeBlocks.BLOCKS.register(eventBus);
        NativeItems.ITEMS.register(eventBus);
        NativeTileEntity.TILE_ENTITIES.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up {}", Reference.MOD_ID);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(Reference.MOD_ID, "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Server starting");
    }
}
