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
package tech.bongers.proximo.common.util;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public final class ProximoProperties {

    private ProximoProperties() {
        // No-args
    }

    /* TEXTURES */
    public static final String PATH_TEXTURES = "textures/";
    public static final String PATH_GUI = PATH_TEXTURES + "gui/";

    /* ITEMS */
    public static final String EDIBLE_REDSTONE = "edible_redstone";
    
    /* BLOCKS */
    public static final String COMPRESSED_REDSTONE_BLOCK = "compressed_redstone_block";

    /* MACHINES */
    public static final String REDSTONE_FURNACE = "redstone_furnace";
    public static final String PACKAGER = "packager";

    /* BLOCK STATES */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    /* MISC */
    public static final String PROXIMO_CREATIVE_TAB = "proximo_tab";
    public static final String CONTAINER_SUFFIX = "_container";
}
