package com.dragn0007.hhamsters.items;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.blocks.HHBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HHItemGroup {

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HamtasticHamsters.MODID);

        public static final RegistryObject<CreativeModeTab> HAMSTERS = CREATIVE_MODE_TABS.register("hamsters",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(HHItems.HAMSTERS.get())).title(Component.translatable("itemGroup.hhamsters"))
                        .displayItems((displayParameters, output) -> {

                            output.accept(HHItems.HAMSTER_SPAWN_EGG.get());
                            output.accept(HHItems.HAMSTER_FOOD.get());
                            output.accept(HHItems.HAMSTER.get());
                            output.accept(HHItems.COOKED_HAMSTER.get());
                            output.accept(HHBlocks.WIRE_PANEL.get());
                            output.accept(HHBlocks.WIRE_PANEL_SINGLE_DOOR.get());
                            output.accept(HHBlocks.WIRE_PANEL_DOOR.get());
                            output.accept(HHBlocks.HAMSTER_BEDDING.get());
                            output.accept(HHBlocks.SPRUCE_HAMSTER_WHEEL.get());
                            output.accept(HHBlocks.SEED_BOWL.get());
                            output.accept(HHBlocks.DUST_BATH.get());
                            output.accept(HHBlocks.SPRUCE_WOOD_HIDE.get());
                            output.accept(HHBlocks.IGLOO_HIDE.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        HHItemGroup.CREATIVE_MODE_TABS.register(eventBus);
    }
}
