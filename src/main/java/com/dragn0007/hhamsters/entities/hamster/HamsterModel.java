package com.dragn0007.hhamsters.entities.hamster;

import com.dragn0007.hhamsters.HamtasticHamsters;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HamsterModel extends GeoModel<Hamster> {

    public enum Variant {
        ALBINO(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_albino.png")),
        BEIGE(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_beige.png")),
        BLACK(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_black.png")),
        BLUE(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_blue.png")),
        CHOCOLATE(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_chocolate.png")),
        CREAM(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_cream.png")),
        GREY(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_grey.png")),
        LAVENDER(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_lavender.png")),
        RUST(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_rust.png")),
        SILVER(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_silver.png")),
        WHITE(new ResourceLocation(HamtasticHamsters.MODID, "textures/entity/hamster/hamster_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }
    public static final ResourceLocation ANIMATION = new ResourceLocation(HamtasticHamsters.MODID, "animations/hamster.animation.json");
    public static final ResourceLocation DWARF_ANIMATION = new ResourceLocation(HamtasticHamsters.MODID, "animations/hamster_dwarf.animation.json");

    @Override
    public ResourceLocation getModelResource(Hamster object) {
        return Hamster.Breed.breedFromOrdinal(object.getBreed()).resourceLocation;
    }

    @Override
    public ResourceLocation getTextureResource(Hamster object) {
        return object.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(Hamster animatable) {
        if (animatable.getBreed() == 2) {
            return DWARF_ANIMATION;
        }
        return ANIMATION;
    }
}

