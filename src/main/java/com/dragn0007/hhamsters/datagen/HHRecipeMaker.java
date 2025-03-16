package com.dragn0007.hhamsters.datagen;

import com.dragn0007.hhamsters.items.HHItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class HHRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public HHRecipeMaker(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HHItems.HAMSTER_FOOD.get(), 2)
                .requires(Items.PUMPKIN_SEEDS)
                .requires(Items.BEETROOT_SEEDS)
                .requires(Items.WHEAT_SEEDS)
                .requires(Items.MELON_SEEDS)
                .unlockedBy("has_seed", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.WHEAT_SEEDS)
                        .build()))
                .save(pFinishedRecipeConsumer);

    }

}