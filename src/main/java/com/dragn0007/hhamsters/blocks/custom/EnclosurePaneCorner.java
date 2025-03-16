package com.dragn0007.hhamsters.blocks.custom;

import com.dragn0007.giddypigs.blocks.DecorRotator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class EnclosurePaneCorner extends DecorRotator {

    public EnclosurePaneCorner() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public boolean isPathfindable(BlockState p_53306_, BlockGetter p_53307_, BlockPos p_53308_, PathComputationType p_53309_) {
        return false;
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(7, 0, 7, 16, 18, 9),
            Block.box(7, 0, 7, 9, 18, 16),
            Block.box(6, 0, 6, 9, 19, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(7, 0, 7, 9, 18, 16),
            Block.box(0, 0, 7, 9, 18, 9),
            Block.box(7, 0, 6, 10, 19, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 7, 9, 18, 9),
            Block.box(7, 0, 0, 9, 18, 9),
            Block.box(7, 0, 7, 10, 19, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(7, 0, 0, 9, 18, 9),
            Block.box(7, 0, 7, 16, 18, 9),
            Block.box(6, 0, 7, 9, 19, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();
}
