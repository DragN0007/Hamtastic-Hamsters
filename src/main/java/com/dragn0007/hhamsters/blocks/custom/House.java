package com.dragn0007.hhamsters.blocks.custom;

import com.dragn0007.giddypigs.blocks.DecorRotator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class House extends DecorRotator {

    public House() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(0, 0, 0, 2, 14, 16),
            Block.box(2, 12, 0, 4, 14, 16),
            Block.box(12, 12, 0, 14, 14, 16),
            Block.box(2, 14, 0, 14, 16, 16),
            Block.box(14, 0, 0, 16, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(0, 0, 0, 16, 14, 2),
            Block.box(0, 12, 2, 16, 14, 4),
            Block.box(0, 12, 12, 16, 14, 14),
            Block.box(0, 14, 2, 16, 16, 14),
            Block.box(0, 0, 14, 16, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 0, 2, 14, 16),
            Block.box(2, 12, 0, 4, 14, 16),
            Block.box(12, 12, 0, 14, 14, 16),
            Block.box(2, 14, 0, 14, 16, 16),
            Block.box(14, 0, 0, 16, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(0, 0, 0, 16, 14, 2),
            Block.box(0, 12, 2, 16, 14, 4),
            Block.box(0, 12, 12, 16, 14, 14),
            Block.box(0, 14, 2, 16, 16, 14),
            Block.box(0, 0, 14, 16, 14, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();
}
