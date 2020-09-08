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

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public final class NativeUtils {

    private NativeUtils() {
        // No-args
    }

    //Method based on thermal expansion 1.12 CoreUtils logic
    public static boolean dropItemStackIntoWorld(final ItemStack stack, final World world, final BlockPos pos) {
        if (!stack.isEmpty()) {
            final float x = world.rand.nextFloat() * 0.8F;
            final float y = world.rand.nextFloat() * 0.8F;
            final float z = world.rand.nextFloat() * 0.8F;

            final ItemEntity entity = new ItemEntity(world, pos.getX() + x, pos.getY() + y, pos.getZ() + z, stack.copy());
            final Vector3d motionPos = new Vector3d(
                    world.rand.nextGaussian() * 0.05F,
                    world.rand.nextGaussian() * 0.05F + 0.2F,
                    world.rand.nextGaussian() * 0.05F
            );
            entity.setMotion(motionPos);
            world.addEntity(entity);
            return true;
        }
        return false;
    }
}
