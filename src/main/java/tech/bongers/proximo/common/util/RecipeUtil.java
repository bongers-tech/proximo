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
package tech.bongers.proximo.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import tech.bongers.proximo.common.inventory.PackagerInventory;
import tech.bongers.proximo.common.item.handler.ProximoItemHandler;

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
    public static IRecipe<?> getRecipeForInventoryAndType(final World world, final ProximoItemHandler inventory, final IRecipeType<?> recipeType) {
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
    public static ICraftingRecipe getCompactingRecipe(final World world, final ItemStack stack, final PackagerInventory grid) {
        if (world != null && stack != null) {
            final PackagerInventory reversion = new PackagerInventory(stack, 1, 1);
            final Set<IRecipe<?>> recipes = findRecipesForType(world, IRecipeType.CRAFTING);
            for (IRecipe<?> recipe : recipes) {
                if (recipe instanceof ICraftingRecipe) {
                    final ICraftingRecipe craftingRecipe = (ICraftingRecipe) recipe;
                    if (!recipeMatchesInventory(world, craftingRecipe, reversion)
                            && grid.hasFullGrid()
                            && recipeMatchesInventory(world, craftingRecipe, grid)) {
                        return craftingRecipe;
                    }
                }
            }
        }
        return null;
    }

    private static boolean recipeMatchesInventory(final World world, final ICraftingRecipe recipe, final PackagerInventory inventory) {
        Objects.requireNonNull(world);
        if (recipe.matches(inventory, world)) {
            final ItemStack result = recipe.getCraftingResult(inventory);
            return !result.isEmpty();
        }
        return false;
    }
}
