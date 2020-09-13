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
package tech.bongers.nativetech.common.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.bongers.nativetech.common.NativeTech;

import static tech.bongers.nativetech.common.util.NativeProperties.*;

public final class NativeContainer {

    private NativeContainer() {
        // No-args
    }

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, NativeTech.MOD_ID);

    public static final RegistryObject<ContainerType<RedstoneFurnaceContainer>> REDSTONE_FURNACE_CONTAINER = CONTAINERS.register(
            REDSTONE_FURNACE + CONTAINER_SUFFIX,
            () -> IForgeContainerType.create(RedstoneFurnaceContainer::new)
    );
    public static final RegistryObject<ContainerType<PackagerContainer>> PACKAGER_CONTAINER = CONTAINERS.register(
            PACKAGER + CONTAINER_SUFFIX,
            () -> IForgeContainerType.create(PackagerContainer::new)
    );

}
