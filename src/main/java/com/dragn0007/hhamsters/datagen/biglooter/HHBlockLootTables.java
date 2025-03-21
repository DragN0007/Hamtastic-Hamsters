package com.dragn0007.hhamsters.datagen.biglooter;

import com.dragn0007.hhamsters.blocks.HHBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class HHBlockLootTables extends BlockLootSubProvider {

    public HHBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    @Override
    protected void generate() {
        dropSelf(HHBlocks.WIRE_PANEL.get());
        dropSelf(HHBlocks.WIRE_PANEL_DOOR.get());
        dropSelf(HHBlocks.WIRE_PANEL_SINGLE_DOOR.get());
        dropSelf(HHBlocks.SPRUCE_HAMSTER_WHEEL.get());
        dropSelf(HHBlocks.HAMSTER_BEDDING.get());
        dropSelf(HHBlocks.SEED_BOWL.get());
        dropSelf(HHBlocks.DUST_BATH.get());
        dropSelf(HHBlocks.SPRUCE_WOOD_HIDE.get());
        dropSelf(HHBlocks.IGLOO_HIDE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HHBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
