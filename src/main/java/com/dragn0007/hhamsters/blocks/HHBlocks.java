package com.dragn0007.hhamsters.blocks;

import com.dragn0007.hhamsters.HamtasticHamsters;
import com.dragn0007.hhamsters.blocks.custom.WirePanel;
import com.dragn0007.hhamsters.blocks.custom.WirePanelDoor;
import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacer;
import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacerContainer;
import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacerEntity;
import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacerItem;
import com.dragn0007.hhamsters.items.HHItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.http.impl.conn.Wire;

import java.util.function.Supplier;

public class HHBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, HamtasticHamsters.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HamtasticHamsters.MODID);

    public static final RegistryObject<Block> WIRE_PANEL = registerBlock("wire_panel", WirePanel::new);
    public static final RegistryObject<Block> WIRE_PANEL_DOOR = registerBlock("wire_panel_door",
            () -> new WirePanelDoor(SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
    public static final RegistryObject<Block> WIRE_PANEL_SINGLE_DOOR = registerBlock("wire_panel_single_door",
            () -> new WirePanelDoor(SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));

    public static final RegistryObject<PixelPlacerContainer> PIXEL_PLACER_CONTAINER = BLOCKS.register("pixel_placer_container", PixelPlacerContainer::new);
    public static final RegistryObject<BlockEntityType<PixelPlacerEntity>> PIXEL_PLACER_ENTITY = BLOCK_ENTITIES.register("pixel_placer_container",
            () -> BlockEntityType.Builder.of(PixelPlacerEntity::new, PIXEL_PLACER_CONTAINER.get()).build(null));

    protected static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends PixelPlacer>RegistryObject<T> registerPixelPlacer(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        HHItems.ITEMS.register("pixel_placement/" + name, () -> new PixelPlacerItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    protected static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        HHItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }
}