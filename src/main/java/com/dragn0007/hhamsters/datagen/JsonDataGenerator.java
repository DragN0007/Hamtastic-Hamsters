package com.dragn0007.hhamsters.datagen;

import com.dragn0007.dragnlivestock.datagen.biglooter.LOLootTableProvider;
import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.datagen.biglooter.HHLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = HamtasticHamsters.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JsonDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new HHRecipeMaker(packOutput));
        generator.addProvider(event.includeClient(), new HHItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), HHLootTableProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new HHWorldGenerator(packOutput, lookupProvider));
    }
}