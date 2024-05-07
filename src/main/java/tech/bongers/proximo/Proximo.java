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
package tech.bongers.proximo;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.bongers.proximo.common.registry.BlockItemRegistry;
import tech.bongers.proximo.common.registry.BlockRegistry;
import tech.bongers.proximo.common.registry.CreativeTabRegistry;
import tech.bongers.proximo.common.registry.ItemRegistry;

@Mod(Proximo.MOD_ID)
public class Proximo {

    public static final String MOD_ID = "proximo";

    public Proximo() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.ITEMS.register(eventBus);
        BlockRegistry.BLOCKS.register(eventBus);
        BlockItemRegistry.BLOCK_ITEMS.register(eventBus);
        CreativeTabRegistry.TABS.register(eventBus);
    }
}
