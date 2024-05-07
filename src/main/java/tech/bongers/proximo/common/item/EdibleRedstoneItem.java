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
package tech.bongers.proximo.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class EdibleRedstoneItem extends Item {

    public EdibleRedstoneItem() {
        super(new Item.Properties()
                .stacksTo(16)
                .food(new FoodProperties.Builder()
                        .nutrition(10)
                        .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200, 2), 1.0f)
                        .alwaysEat()
                        .build()));
    }
}
