package com.dragn0007.hhamsters.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class WirePanelDoor extends DecorRotatorWire implements SimpleWaterloggedBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    private final net.minecraft.sounds.SoundEvent openSound;
    private final net.minecraft.sounds.SoundEvent closeSound;

    public WirePanelDoor(net.minecraft.sounds.SoundEvent openSound, net.minecraft.sounds.SoundEvent closeSound) {
        super(NORTH, EAST, SOUTH, WEST);
        this.openSound = openSound;
        this.closeSound = closeSound;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)));
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(OPEN)) {
            state = state.setValue(OPEN, Boolean.valueOf(false));
            level.setBlock(pos, state, 10);
        } else {
            state = state.setValue(OPEN, Boolean.valueOf(true));
            level.setBlock(pos, state, 10);
        }

        boolean flag = state.getValue(OPEN);
        level.playSound(player, pos, flag ? this.openSound : this.closeSound, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
        level.gameEvent(player, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        boolean flag = level.hasNeighborSignal(blockpos);
        Direction direction = context.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction).setValue(OPEN, Boolean.valueOf(flag));
    }

    public boolean isPathfindable(BlockState p_53360_, BlockGetter p_53361_, BlockPos p_53362_, PathComputationType p_53363_) {
        switch (p_53363_) {
            case LAND:
                return p_53360_.getValue(OPEN);
            default:
                return false;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53389_) {
        p_53389_.add(FACING, OPEN);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter p_53397_, BlockPos p_53398_, CollisionContext p_53399_) {
        if (state.getValue(OPEN)) {
            return Shapes.empty();
        }
        Direction facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            default:
                return Shapes.empty();
        }
    }

    public VoxelShape getOcclusionShape(BlockState state, BlockGetter p_53402_, BlockPos p_53403_) {
        Direction facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            default:
                return Shapes.empty();
        }
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(0, 0, 0, 16, 21, 2)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(14, 0, 0, 16, 21, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 14, 16, 21, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(0, 0, 0, 2, 21, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }
}
