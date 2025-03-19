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

public class IglooHide extends DecorRotatorWire implements SimpleWaterloggedBlock {

    public IglooHide() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public boolean isPathfindable(BlockState p_53306_, BlockGetter p_53307_, BlockPos p_53308_, PathComputationType p_53309_) {
        return false;
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(2, 0, 0, 4, 5, 6),
            Block.box(3, 5, 6, 5, 9, 14),
            Block.box(5, 5, 14, 11, 9, 16),
            Block.box(11, 5, 6, 13, 9, 14),
            Block.box(1, 0, 6, 3, 5, 14),
            Block.box(13, 0, 6, 15, 5, 14),
            Block.box(3, 0, 14, 13, 5, 16),
            Block.box(12, 0, 0, 14, 5, 6),
            Block.box(11, 5, 0, 13, 7, 6),
            Block.box(5, 7, 0, 11, 9, 6),
            Block.box(5, 9, 6, 11, 11, 14),
            Block.box(3, 5, 0, 5, 7, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(10, 0, 2, 16, 5, 4),
            Block.box(2, 5, 3, 10, 9, 5),
            Block.box(0, 5, 5, 2, 9, 11),
            Block.box(2, 5, 11, 10, 9, 13),
            Block.box(2, 0, 1, 10, 5, 3),
            Block.box(2, 0, 13, 10, 5, 15),
            Block.box(0, 0, 3, 2, 5, 13),
            Block.box(10, 0, 12, 16, 5, 14),
            Block.box(10, 5, 11, 16, 7, 13),
            Block.box(10, 7, 5, 16, 9, 11),
            Block.box(2, 9, 5, 10, 11, 11),
            Block.box(10, 5, 3, 16, 7, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(12, 0, 10, 14, 5, 16),
            Block.box(11, 5, 2, 13, 9, 10),
            Block.box(5, 5, 0, 11, 9, 2),
            Block.box(3, 5, 2, 5, 9, 10),
            Block.box(13, 0, 2, 15, 5, 10),
            Block.box(1, 0, 2, 3, 5, 10),
            Block.box(3, 0, 0, 13, 5, 2),
            Block.box(2, 0, 10, 4, 5, 16),
            Block.box(3, 5, 10, 5, 7, 16),
            Block.box(5, 7, 10, 11, 9, 16),
            Block.box(5, 9, 2, 11, 11, 10),
            Block.box(11, 5, 10, 13, 7, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(0, 0, 12, 6, 5, 14),
            Block.box(6, 5, 11, 14, 9, 13),
            Block.box(14, 5, 5, 16, 9, 11),
            Block.box(6, 5, 3, 14, 9, 5),
            Block.box(6, 0, 13, 14, 5, 15),
            Block.box(6, 0, 1, 14, 5, 3),
            Block.box(14, 0, 3, 16, 5, 13),
            Block.box(0, 0, 2, 6, 5, 4),
            Block.box(0, 5, 3, 6, 7, 5),
            Block.box(0, 7, 5, 6, 9, 11),
            Block.box(6, 9, 5, 14, 11, 11),
            Block.box(0, 5, 11, 6, 7, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }
}
