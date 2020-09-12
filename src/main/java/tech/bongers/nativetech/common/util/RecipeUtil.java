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

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import tech.bongers.nativetech.common.handler.NativeItemHandler;
import tech.bongers.nativetech.common.inventory.CompactingInventory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class RecipeUtil {

    private RecipeUtil() {
        // No-args
    }

    public static Set<IRecipe<?>> findRecipesForType(final World world, final IRecipeType<?> recipeType) {
        return world == null
                ? Collections.emptySet()
                : world.getRecipeManager().getRecipes()
                .stream()
                .filter(recipe -> recipe.getType() == recipeType)
                .collect(Collectors.toSet());
    }

    @Nullable
    public static IRecipe<?> getRecipeForInventoryAndType(final World world, final NativeItemHandler inventory, final IRecipeType<?> recipeType) {
        if (world != null && inventory != null) {
            final Set<IRecipe<?>> recipes = findRecipesForType(world, recipeType);
            for (IRecipe<?> iRecipe : recipes) {
                BlastingRecipe recipe = (BlastingRecipe) iRecipe;
                if (recipe.matches(new RecipeWrapper(inventory), world)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    @Nullable
    public static ICraftingRecipe getCompactingRecipe(final World world, final ItemStack stack) {
        if (world != null && stack != null) {

            final CompactingInventory reversion = new CompactingInventory(stack, 1, 1);
            final CompactingInventory smallGrid = new CompactingInventory(stack, 2, 2);
            final CompactingInventory largeGrid = new CompactingInventory(stack, 3, 3);

            final Set<IRecipe<?>> recipes = findRecipesForType(world, IRecipeType.CRAFTING);
            for (IRecipe<?> recipe : recipes) {
                if (recipe instanceof ICraftingRecipe) {
                    final ICraftingRecipe craftingRecipe = (ICraftingRecipe) recipe;
                    if (!recipeMatchesInventory(world, craftingRecipe, reversion)
                            && (recipeMatchesInventory(world, craftingRecipe, smallGrid)
                            || recipeMatchesInventory(world, craftingRecipe, largeGrid))) {
                        return craftingRecipe;
                    }
                }
            }
        }
        return null;
    }

    private static boolean recipeMatchesInventory(final World world, final ICraftingRecipe recipe, final CompactingInventory inventory) {
        Objects.requireNonNull(world);
        if (recipe.matches(inventory, world)) {
            final ItemStack result = recipe.getCraftingResult(inventory);
            return !result.isEmpty();
        }
        return false;
    }
}
