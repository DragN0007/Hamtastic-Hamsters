package com.dragn0007.hhamsters.gui;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.entities.hamster.Hamster;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HamsterScreen extends AbstractContainerScreen<HamsterMenu> {
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(HamtasticHamsters.MODID, "textures/gui/hamster.png");
    public final Hamster hamster;

    public HamsterScreen(HamsterMenu hamsterMenu, Inventory inventory, Component component) {
        super(hamsterMenu, inventory, component);
        this.hamster = hamsterMenu.hamster;
        this.imageHeight = 133;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void renderBg(GuiGraphics graphics, float p_282998_, int p_282929_, int p_283133_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(INVENTORY_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);

        if (this.hamster.isTame()) {
            graphics.blit(INVENTORY_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
        }
    }

    @Override
    public void render(GuiGraphics p_281697_, int p_282103_, int p_283529_, float p_283079_) {
        this.renderBackground(p_281697_);
        super.render(p_281697_, p_282103_, p_283529_, p_283079_);
        this.renderTooltip(p_281697_, p_282103_, p_283529_);
    }
}

