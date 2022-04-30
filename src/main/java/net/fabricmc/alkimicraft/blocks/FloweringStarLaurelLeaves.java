package net.fabricmc.alkimicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class FloweringStarLaurelLeaves extends LeavesBlock {
    public static final BooleanProperty LIT;
    public static final BooleanProperty MATURE;

    public FloweringStarLaurelLeaves(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(Properties.LIT, false).with(MATURE,false));
    }

    public boolean hasRandomTicks(BlockState blockState) {
        return !blockState.get(MATURE) || blockState.get(DISTANCE) == 7 && !(Boolean)blockState.get(PERSISTENT);
//        return (Integer)blockState.get(DISTANCE) == 7 && !(Boolean)blockState.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (!(Boolean)blockState.get(PERSISTENT) && blockState.get(DISTANCE) == 7) {
            dropStacks(blockState, serverWorld, blockPos);
            serverWorld.removeBlock(blockPos, false);
        }
        if (serverWorld.getMoonPhase() == 4 && serverWorld.getTimeOfDay()%24000 >= 15000 && serverWorld.getTimeOfDay()%24000 <= 22300){
            serverWorld.setBlockState(blockPos, blockState.with(Properties.LIT, true),3);
        }else {
            if (blockState.get(LIT)){
                serverWorld.setBlockState(blockPos, blockState.with(Properties.LIT, false).with(MATURE, true),3);
            }
        }
//        super.randomTick(blockState, serverWorld, blockPos, random);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {builder.add(LIT, MATURE, DISTANCE, PERSISTENT);}


    static {
        LIT = Properties.LIT;
        MATURE = Properties.BERRIES;
    }
}
