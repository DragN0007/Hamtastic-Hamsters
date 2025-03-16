package com.dragn0007.hhamsters.items;

import com.dragn0007.hhamsters.HamtasticHamsters;
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
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(HHItems.HAMSTERS.get())).title(Component.translatable("itemGroup.giddy_pigs"))
                        .displayItems((displayParameters, output) -> {


                    }).build());

    public static void register(IEventBus eventBus) {
        HHItemGroup.CREATIVE_MODE_TABS.register(eventBus);
    }
}
