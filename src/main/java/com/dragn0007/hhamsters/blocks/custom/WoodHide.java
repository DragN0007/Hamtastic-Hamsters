package com.dragn0007.hhamsters.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class WoodHide extends DecorRotatorWire implements SimpleWaterloggedBlock {

    public WoodHide() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public boolean isPathfindable(BlockState p_53306_, BlockGetter p_53307_, BlockPos p_53308_, PathComputationType p_53309_) {
        return false;
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(3, 0, 5, 4, 8, 15),
            Block.box(0, 5, 10, 3, 6, 12),
            Block.box(0, 4, 8, 3, 5, 10),
            Block.box(0, 3, 6, 3, 4, 8),
            Block.box(0, 2, 4, 3, 3, 6),
            Block.box(0, 1, 2, 3, 2, 4),
            Block.box(0, 0, 0, 3, 1, 2),
            Block.box(0, 6, 12, 3, 7, 14),
            Block.box(0, 7, 14, 3, 8, 16),
            Block.box(15, 0, 5, 16, 11, 16),
            Block.box(4, 7, 5, 15, 8, 15),
            Block.box(3, 0, 15, 15, 11, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(1, 0, 3, 11, 8, 4),
            Block.box(4, 5, 0, 6, 6, 3),
            Block.box(6, 4, 0, 8, 5, 3),
            Block.box(8, 3, 0, 10, 4, 3),
            Block.box(10, 2, 0, 12, 3, 3),
            Block.box(12, 1, 0, 14, 2, 3),
            Block.box(14, 0, 0, 16, 1, 3),
            Block.box(2, 6, 0, 4, 7, 3),
            Block.box(0, 7, 0, 2, 8, 3),
            Block.box(0, 0, 15, 11, 11, 16),
            Block.box(1, 7, 4, 11, 8, 15),
            Block.box(0, 0, 3, 1, 11, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(12, 0, 1, 13, 8, 11),
            Block.box(13, 5, 4, 16, 6, 6),
            Block.box(13, 4, 6, 16, 5, 8),
            Block.box(13, 3, 8, 16, 4, 10),
            Block.box(13, 2, 10, 16, 3, 12),
            Block.box(13, 1, 12, 16, 2, 14),
            Block.box(13, 0, 14, 16, 1, 16),
            Block.box(13, 6, 2, 16, 7, 4),
            Block.box(13, 7, 0, 16, 8, 2),
            Block.box(0, 0, 0, 1, 11, 11),
            Block.box(1, 7, 1, 12, 8, 11),
            Block.box(1, 0, 0, 13, 11, 1)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(5, 0, 12, 15, 8, 13),
            Block.box(10, 5, 13, 12, 6, 16),
            Block.box(8, 4, 13, 10, 5, 16),
            Block.box(6, 3, 13, 8, 4, 16),
            Block.box(4, 2, 13, 6, 3, 16),
            Block.box(2, 1, 13, 4, 2, 16),
            Block.box(0, 0, 13, 2, 1, 16),
            Block.box(12, 6, 13, 14, 7, 16),
            Block.box(14, 7, 13, 16, 8, 16),
            Block.box(5, 0, 0, 16, 11, 1),
            Block.box(5, 7, 1, 15, 8, 12),
            Block.box(15, 0, 1, 16, 11, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }
}
