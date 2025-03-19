package com.dragn0007.hhamsters.datagen;

import com.dragn0007.hhamsters.items.HHItems;
import com.dragn0007.hhamsters.blocks.HHBlocks;
import com.dragn0007.hhamsters.items.HHItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class HHRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public HHRecipeMaker(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(HHItems.HAMSTER.get()), RecipeCategory.MISC, HHItems.COOKED_HAMSTER.get(), 0.35F, 100)
                .unlockedBy("has_hamster", has(HHItems.HAMSTER.get())).save(pFinishedRecipeConsumer, new ResourceLocation("hhamsters", "cooked_hamster_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(HHItems.HAMSTER.get()), RecipeCategory.MISC, HHItems.COOKED_HAMSTER.get(), 0.35F, 200)
                .unlockedBy("has_hamster", has(HHItems.HAMSTER.get())).save(pFinishedRecipeConsumer, new ResourceLocation("hhamsters", "cooked_hamster_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(HHItems.HAMSTER.get()), RecipeCategory.MISC, HHItems.COOKED_HAMSTER.get(), 0.35F, 600)
                .unlockedBy("has_hamster", has(HHItems.HAMSTER.get())).save(pFinishedRecipeConsumer, new ResourceLocation("hhamsters", "cooked_hamster_campfire_cooking"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.IGLOO_HIDE.get())
                .define('A', Blocks.WHITE_WOOL)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("A A")
                .unlockedBy("has_wool", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.WHITE_WOOL).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.SPRUCE_WOOD_HIDE.get())
                .define('A', Blocks.SPRUCE_PLANKS)
                .define('B', Items.STICK)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("BAA")
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.SPRUCE_PLANKS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.DUST_BATH.get())
                .define('A', Items.STICK)
                .define('B', Items.SAND)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_sand", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.SAND).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.SEED_BOWL.get())
                .define('A', Items.STICK)
                .define('B', HHItems.HAMSTER_FOOD.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.SPRUCE_PLANKS).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HHBlocks.HAMSTER_BEDDING.get(), 16)
                .requires(Items.PAPER)
                .requires(Items.PAPER)
                .requires(Items.STICK)
                .requires(Items.STICK)
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK)
                        .build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.SPRUCE_HAMSTER_WHEEL.get())
                .define('A', Blocks.SPRUCE_PLANKS)
                .define('B', Items.STICK)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.SPRUCE_PLANKS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.WIRE_PANEL.get(), 8)
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.IRON_INGOT)
                .pattern("BAB")
                .pattern("BAB")
                .unlockedBy("has_iron", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.WIRE_PANEL_SINGLE_DOOR.get(), 2)
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.IRON_INGOT)
                .pattern("BAA")
                .pattern("BAA")
                .unlockedBy("has_iron", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HHBlocks.WIRE_PANEL_DOOR.get(), 2)
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.IRON_INGOT)
                .pattern("ABA")
                .pattern("BAB")
                .unlockedBy("has_iron", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

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