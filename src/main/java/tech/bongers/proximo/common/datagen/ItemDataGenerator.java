package tech.bongers.proximo.common.datagen;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import tech.bongers.proximo.Proximo;

@EventBusSubscriber(modid = Proximo.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ItemDataGenerator {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        LOGGER.info("Gathering data");

        final DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new ItemRecipes(generator.getPackOutput(), event.getLookupProvider()));
    }
}
