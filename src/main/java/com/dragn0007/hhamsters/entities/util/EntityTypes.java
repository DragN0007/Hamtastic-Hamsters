package com.dragn0007.hhamsters.entities.util;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.hhamsters.entities.hamster.Hamster;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.hhamsters.HamtasticHamsters.MODID;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<Hamster>> HAMSTER_ENTITY = ENTITY_TYPES.register("hamster",
            () -> EntityType.Builder.of(Hamster::new,
                            MobCategory.CREATURE)
                    .sized(0.4f,0.4f)
                    .build(new ResourceLocation(LivestockOverhaul.MODID,"hamster").toString()));

}