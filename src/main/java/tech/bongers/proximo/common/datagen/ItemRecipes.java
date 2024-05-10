package tech.bongers.proximo.common.datagen;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import tech.bongers.proximo.common.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ItemRecipes extends RecipeProvider {

    public ItemRecipes(final PackOutput output, final CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void buildRecipes(final RecipeOutput recipeOutput) {
        LogUtils.getLogger().error("RECIPE OUTPUT");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ENERGIZED_REDSTONE.get())
                .pattern("grg")
                .define('g', Items.GLOWSTONE_DUST)
                .define('r', Items.REDSTONE)
                .group("proximo")
                .unlockedBy("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
                .save(recipeOutput);
    }
}
