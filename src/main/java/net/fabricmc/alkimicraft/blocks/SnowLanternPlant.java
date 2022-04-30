package net.fabricmc.alkimicraft.blocks;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class SnowLanternPlant extends PlantBlock {
    public static final BooleanProperty LIT;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public SnowLanternPlant(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(Properties.LIT, false));
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        if (serverWorld.getMoonPhase() == 4 && serverWorld.getTimeOfDay()%24000 >= 13000 && serverWorld.getTimeOfDay()%24000 <= 22300){
                serverWorld.setBlockState(blockPos, blockState.with(Properties.LIT, true), 3);
        }else {
            serverWorld.setBlockState(blockPos, blockState.with(Properties.LIT, false),3);
        }
    }

    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        Vec3d vec3d = blockState.getModelOffset(blockView, blockPos);
        return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

//    @Override
//    public void scheduledTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
//        if (serverWorld.getMoonPhase() == 4 && serverWorld.isNight()){
//            serverWorld.setBlockState(blockPos, blockState.with(LIT, true),3);
//        }
//    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {builder.add(LIT);}


    static {
        LIT = Properties.LIT;
    }
}
