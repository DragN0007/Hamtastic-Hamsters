package com.dragn0007.hhamsters.event;

import com.dragn0007.dragnlivestock.entities.chicken.OChicken;
import com.dragn0007.dragnlivestock.entities.horse.OHorseRender;
import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.blocks.HHBlocks;
import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacerEntityRenderer;
import com.dragn0007.hhamsters.entities.hamster.Hamster;
import com.dragn0007.hhamsters.entities.hamster.HamsterModel;
import com.dragn0007.hhamsters.entities.hamster.HamsterRender;
import com.dragn0007.hhamsters.entities.util.EntityTypes;
import com.dragn0007.hhamsters.gui.HHMenuTypes;
import com.dragn0007.hhamsters.gui.HamsterScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = HamtasticHamsters.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class HamtasticHamstersEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityTypes.HAMSTER_ENTITY.get(), Hamster.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypes.HAMSTER_ENTITY.get(), HamsterRender::new);
        MenuScreens.register(HHMenuTypes.HAMSTER_MENU.get(), HamsterScreen::new);
    }

    @SubscribeEvent
    public static void entityRendererEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(HHBlocks.PIXEL_PLACER_ENTITY.get(), PixelPlacerEntityRenderer::new);
    }
}