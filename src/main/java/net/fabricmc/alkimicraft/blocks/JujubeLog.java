package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class JujubeLog extends ThinTreeLog {
    public JujubeLog(Settings settings) {
        super(settings, BlockInit.SANDY_LOAM);
    }

    public BlockState withConnectionProperties(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState2 = world.getBlockState(pos.up());
        BlockState blockState3 = world.getBlockState(pos.north());
        BlockState blockState4 = world.getBlockState(pos.east());
        BlockState blockState5 = world.getBlockState(pos.south());
        BlockState blockState6 = world.getBlockState(pos.west());
        return this.getDefaultState().with(DOWN, blockState.isOf(this) || blockState.isOf(BlockInit.JUJUBE_TOP) || canGrowOn(blockState) || blockState.isOf(BlockInit.JUJUBE_LEAVES))
                .with(UP, blockState2.isOf(this) || blockState2.isOf(BlockInit.JUJUBE_TOP) || blockState.isOf(BlockInit.JUJUBE_LEAVES))
                .with(NORTH, blockState3.isOf(this) || blockState.isOf(BlockInit.JUJUBE_LEAVES))
                .with(EAST, blockState4.isOf(this)|| blockState.isOf(BlockInit.JUJUBE_LEAVES))
                .with(SOUTH, blockState5.isOf(this)|| blockState.isOf(BlockInit.JUJUBE_LEAVES))
                .with(WEST, blockState6.isOf(this)|| blockState.isOf(BlockInit.JUJUBE_LEAVES));
    }

    public Boolean canGrowOn(BlockState blockState){
        return blockState.isOf(soilBlock) || blockState.isIn(BlockTags.DIRT);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            boolean bl = neighborState.isOf(this) || neighborState.isOf(BlockInit.JUJUBE_TOP) || direction == Direction.DOWN && canGrowOn(neighborState)|| neighborState.isOf(BlockInit.JUJUBE_LEAVES);
            return state.with(FACING_PROPERTIES.get(direction), bl);
        }
    }
}
