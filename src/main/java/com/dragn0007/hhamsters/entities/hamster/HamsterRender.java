package com.dragn0007.hhamsters.entities.hamster;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HamsterRender extends GeoEntityRenderer<Hamster> {

    public HamsterRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HamsterModel());
        this.addRenderLayer(new HamsterMarkingLayer(this));
    }

    @Override
    public void render(Hamster entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.hasFullInventory()) {
            model.getBone("cheeks").ifPresent(b -> b.setHidden(false));
        } else {
            model.getBone("cheeks").ifPresent(b -> b.setHidden(true));
        }

        if(entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}


