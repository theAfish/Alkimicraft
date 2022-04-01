package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.Random;

public class ThinTreeLog extends ConnectingBlock {
    protected final Block soilBlock;

    public ThinTreeLog(Settings settings, Block soilBlock) {
        super(0.3125F, settings);
        this.soilBlock = soilBlock;
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.withConnectionProperties(ctx.getWorld(), ctx.getBlockPos());
    }

    public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState2 = world.getBlockState(pos.up());
        BlockState blockState3 = world.getBlockState(pos.north());
        BlockState blockState4 = world.getBlockState(pos.east());
        BlockState blockState5 = world.getBlockState(pos.south());
        BlockState blockState6 = world.getBlockState(pos.west());
        return this.getDefaultState().with(DOWN, blockState.isOf(this) || blockState.isOf(BlockInit.PENCIL_PLANT_TOP) || blockState.isOf(BlockInit.LOAMY_SAND)).with(UP, blockState2.isOf(this) || blockState2.isOf(BlockInit.PENCIL_PLANT_TOP)).with(NORTH, blockState3.isOf(this)).with(EAST, blockState4.isOf(this)).with(SOUTH, blockState5.isOf(this)).with(WEST, blockState6.isOf(this));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            boolean bl = neighborState.isOf(this) || neighborState.isOf(BlockInit.PENCIL_PLANT_TOP) || direction == Direction.DOWN && neighborState.isOf(BlockInit.LOAMY_SAND);
            return state.with(FACING_PROPERTIES.get(direction), bl);
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        boolean bl = !world.getBlockState(pos.up()).isAir();// && !blockState.isAir();
        Iterator var6 = Direction.Type.HORIZONTAL.iterator();

        BlockState blockState3;
        do {
            BlockPos blockPos;
            BlockState blockState2;
            do {
                if (!var6.hasNext()) {
                    return blockState.isOf(this) || canGrowOn(blockState);
                }

                Direction direction = (Direction)var6.next();
                blockPos = pos.offset(direction);
                blockState2 = world.getBlockState(blockPos);
            } while(!blockState2.isOf(this));

//            if (bl) {
//                return false;
//            }

            blockState3 = world.getBlockState(blockPos.down());
        } while(!blockState3.isOf(this) && !canGrowOn(blockState3));

        return true;
    }

    public Boolean canGrowOn(BlockState blockState){
        return blockState.isOf(soilBlock);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
