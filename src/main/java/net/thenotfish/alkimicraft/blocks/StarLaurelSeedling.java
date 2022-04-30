package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class StarLaurelSeedling extends CropBlock {
    public static final IntProperty AGE;
    private static final VoxelShape[] AGE_TO_SHAPE;

    public StarLaurelSeedling(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE,0));
    }

    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (serverWorld.getBaseLightLevel(blockPos, 0) >= 9) {
            int i = this.getAge(blockState);
            if (i < this.getMaxAge()) {
                if (random.nextInt(11) == 0) {
                    serverWorld.setBlockState(blockPos, this.withAge(i + 1), 2);
                }
            } else {
                if (random.nextInt(5) == 0){
                    serverWorld.setBlockState(blockPos, BlockInit.STAR_LAUREL_SAPLING.getDefaultState());
                }else {
                    serverWorld.setBlockState(blockPos, BlockInit.SNOW_LANTERN_PLANT.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState blockState) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state);
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {
        this.applyGrowth(serverWorld, blockPos, blockState);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }


    static {
        AGE = Properties.AGE_7;
        AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D),
                Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D),
                Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D)};
    }
}
