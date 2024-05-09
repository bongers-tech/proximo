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
package tech.bongers.proximo.legacy.common.container;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import tech.bongers.proximo.Proximo;

public final class ProximoContainer {

    private ProximoContainer() {
        // No-args
    }

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, Proximo.MOD_ID);

    /*public static final RegistryObject<MenuType<RedstoneFurnaceContainer>> REDSTONE_FURNACE_CONTAINER = MENU_TYPES.register(
            REDSTONE_FURNACE + CONTAINER_SUFFIX,
            () -> IForgeContainerType.create(RedstoneFurnaceContainer::new)
    );
    public static final RegistryObject<ContainerType<PackagerContainer>> PACKAGER_CONTAINER = MENU_TYPES.register(
            PACKAGER + CONTAINER_SUFFIX,
            () -> IForgeContainerType.create(PackagerContainer::new)
    );*/

}
