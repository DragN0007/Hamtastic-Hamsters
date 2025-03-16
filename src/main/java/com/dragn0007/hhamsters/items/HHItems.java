package com.dragn0007.hhamsters.items;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.entities.util.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HHItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HamtasticHamsters.MODID);

    public static final RegistryObject<Item> HAMSTER_SPAWN_EGG = ITEMS.register("hamster_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.HAMSTER_ENTITY, 0xe6bb8c, 0xf3efec, new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> HAMSTER_FOOD = ITEMS.register("hamster_food",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HAMSTERS = ITEMS.register("hamsters",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}