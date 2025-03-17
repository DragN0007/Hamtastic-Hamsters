package com.dragn0007.hhamsters.entities.hamster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HamsterRender extends GeoEntityRenderer<Hamster> {

    public HamsterRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HamsterModel());
        this.addRenderLayer(new HamsterMarkingLayer(this));
        this.addRenderLayer(new HamsterDwarfStripeLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, Hamster entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if(entity.isFed()) {
            model.getBone("cheeks").ifPresent(b -> b.setHidden(false));
        } else {
            model.getBone("cheeks").ifPresent(b -> b.setHidden(true));
        }

        if(entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}


