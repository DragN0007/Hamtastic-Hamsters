package com.dragn0007.hhamsters.util;

import com.dragn0007.hhamsters.HamtasticHamsters;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class HHTags {

    public static class Items {

        public static final TagKey<Item> HAMSTER_FOOD = tag("hamster_food");

        private static TagKey<Item> tag (String name) {
            return ItemTags.create(new ResourceLocation(HamtasticHamsters.MODID, name));
        }
        private static TagKey<Item> forgeTag (String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}
