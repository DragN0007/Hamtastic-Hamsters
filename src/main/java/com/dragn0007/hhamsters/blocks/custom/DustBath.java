package com.dragn0007.hhamsters.blocks.custom;

import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DustBath extends PixelPlacer {
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-4, 0, -4, 4, 1, 4)
    );

    public DustBath() {
        super(Properties.copy(Blocks.OAK_PLANKS).noOcclusion());
    }

    @Override
    public VoxelShape getVoxelShape(Direction direction) {
        return SHAPE;
    }
}
