package com.dragn0007.hhamsters.blocks.custom;

import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeedBowl extends PixelPlacer {
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-2.5, 0, -2.5, 2.5, 1, 2.5)
    );

    public SeedBowl() {
        super(Properties.copy(Blocks.OAK_PLANKS).noOcclusion());
    }

    @Override
    public VoxelShape getVoxelShape(Direction direction) {
        return SHAPE;
    }
}
