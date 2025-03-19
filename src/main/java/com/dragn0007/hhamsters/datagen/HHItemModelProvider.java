package com.dragn0007.hhamsters.datagen;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.items.HHItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class HHItemModelProvider extends ItemModelProvider {
    public HHItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HamtasticHamsters.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        simpleItem(HHItems.HAMSTERS);
        simpleItem(HHItems.HAMSTER_FOOD);
        simpleItem(HHItems.HAMSTER);
        simpleItem(HHItems.COOKED_HAMSTER);
    }

    public ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HamtasticHamsters.MODID,"item/" + item.getId().getPath()));
    }
}