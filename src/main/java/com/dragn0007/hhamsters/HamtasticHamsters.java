package com.dragn0007.hhamsters;

import com.dragn0007.hhamsters.blocks.HHBlocks;
import com.dragn0007.hhamsters.entities.util.EntityTypes;
import com.dragn0007.hhamsters.gui.HHMenuTypes;
import com.dragn0007.hhamsters.items.HHItemGroup;
import com.dragn0007.hhamsters.items.HHItems;
import com.dragn0007.hhamsters.util.HamtasticHamstersCommonConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(HamtasticHamsters.MODID)
public class HamtasticHamsters
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "hhamsters";

    public HamtasticHamsters()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        HHItems.register(eventBus);
        HHBlocks.register(eventBus);
        HHItemGroup.register(eventBus);
        HHMenuTypes.register(eventBus);
        EntityTypes.ENTITY_TYPES.register(eventBus);

        GeckoLib.initialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HamtasticHamstersCommonConfig.SPEC, "hamsters-common.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final EntityDataSerializer<ResourceLocation> RESOURCE_LOCATION = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buf, ResourceLocation resourceLocation) {
            buf.writeResourceLocation(resourceLocation);
        }

        @Override
        public ResourceLocation read(FriendlyByteBuf buf) {
            return buf.readResourceLocation();
        }

        @Override
        public ResourceLocation copy(ResourceLocation resourceLocation) {
            return resourceLocation;
        }
    };

    static {
        EntityDataSerializers.registerSerializer(RESOURCE_LOCATION);
    }
}