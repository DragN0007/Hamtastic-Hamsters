package com.dragn0007.hhamsters.entities.hamster;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.unicorn.Unicorn;
import com.dragn0007.hhamsters.HamtasticHamsters;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class HamsterModel extends DefaultedEntityGeoModel<Hamster> {

    public HamsterModel() {
        super(new ResourceLocation(HamtasticHamsters.MODID, "hamster"), true);
    }

    @Override
    public void setCustomAnimations(Hamster animatable, long instanceId, AnimationState<Hamster> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        ALBINO(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_albino.png")),
        BEIGE(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_beige.png")),
        BLACK(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_black.png")),
        BLUE(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_blue.png")),
        CHOCOLATE(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_chocolate.png")),
        CREAM(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_cream.png")),
        GREY(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_grey.png")),
        LAVENDER(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_lavender.png")),
        RUST(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_rust.png")),
        SILVER(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_silver.png")),
        WHITE(new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/hamster_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }
    public static final ResourceLocation ANIMATION = new ResourceLocation(HamtasticHamsters.MODID, "animations/hamster.animation.json");
    public static final ResourceLocation DWARF_ANIMATION = new ResourceLocation(HamtasticHamsters.MODID, "animations/hamster_dwarf.animation.json");
    public static final ResourceLocation BABY = new ResourceLocation(HamtasticHamsters.MODID, "textures/hamster/baby/hamster_baby.png");

    @Override
    public ResourceLocation getModelResource(Hamster object) {
        return Hamster.Breed.breedFromOrdinal(object.getBreed()).resourceLocation;
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(Hamster object) {
        if (object.isBaby()) {
            return BABY;
        }
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(Hamster animatable) {
        if (animatable.getBreed() == 2) {
            return DWARF_ANIMATION;
        }
        return ANIMATION;
    }
}

