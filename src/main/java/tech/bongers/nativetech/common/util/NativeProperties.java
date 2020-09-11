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

import net.minecraft.state.BooleanProperty;

public final class NativeProperties {

    private NativeProperties() {
        // No-args
    }

    /* TEXTURES */
    public static final String PATH_TEXTURES = "textures/";
    public static final String PATH_GUI = PATH_TEXTURES + "gui/";

    /* BLOCKS */
    public static final String COMPRESSED_REDSTONE_BLOCK = "compressed_redstone_block";
    public static final String DOUBLE_COMPRESSED_REDSTONE_BLOCK = "double_compressed_redstone_block";

    /* MACHINES */
    public static final String REDSTONE_FURNACE = "redstone_furnace";
    public static final String COMPACTOR = "compactor";

    /* BLOCK STATES */
    public static final BooleanProperty FACING = BooleanProperty.create("facing");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
}
