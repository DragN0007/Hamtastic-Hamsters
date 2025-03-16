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

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HHBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
