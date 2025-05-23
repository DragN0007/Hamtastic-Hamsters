package com.dragn0007.hhamsters.entities.hamster;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HamsterDwarfStripeLayer extends GeoRenderLayer<Hamster> {
    public HamsterDwarfStripeLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Hamster animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if(!(animatable.getBreed() == 2)) {
            return;
        }

        RenderType renderMarkingType = RenderType.entityCutout(((Hamster)animatable).getOverlayLocation());
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderMarkingType,
                bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

    public enum Overlay {
        NONE(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/pattern/none.png")),
        BACKSPLASH(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/dwarf/dwarf_hamster_stripe_overlay.png"));

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay patternFromOrdinal(int pattern) { return Overlay.values()[pattern % Overlay.values().length];
        }
    }

}
