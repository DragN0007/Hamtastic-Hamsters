package com.dragn0007.hhamsters.blocks.custom;

import com.dragn0007.hhamsters.blocks.pixel_placement.util.PixelPlacer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HamsterWheel extends PixelPlacer {
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(-4, 0, -5, 4, 11, 4)
    );

    public HamsterWheel() {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion());
    }

    @Override
    public VoxelShape getVoxelShape(Direction direction) {
        return SHAPE;
    }
}
