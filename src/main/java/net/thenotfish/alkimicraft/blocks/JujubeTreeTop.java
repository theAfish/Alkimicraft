package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class JujubeTreeTop extends AbstractThinTreeTop {
    public static final BooleanProperty BOTTOM;
    protected static final VoxelShape[] AGE_TO_SHAPE;


    public JujubeTreeTop(Settings settings) {
        super(BlockInit.JUJUBE_LOG, BlockInit.JUJUBE_LEAVES, BlockInit.SANDY_LOAM, settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(BOTTOM, true));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(BlockInit.JUJUBE_LOG) || blockState.isIn(BlockTags.DIRT) || blockState.isOf(BlockInit.SANDY_LOAM);
    }

    @Override
    public boolean canGrowOn(BlockState blockState) {
        return blockState.isIn(BlockTags.DIRT) || blockState.isOf(BlockInit.SANDY_LOAM);
    }

    protected void grow(World world, BlockPos pos, int age) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, age).with(BOTTOM, false), 2);
//        world.syncWorldEvent(1033, pos, 0);
    }

    protected void die(World world, BlockPos pos) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, 5).with(BOTTOM, false), 2);
//        world.syncWorldEvent(1034, pos, 0);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, BOTTOM);
    }

    static {
        BOTTOM = Properties.BOTTOM;
        AGE_TO_SHAPE = new VoxelShape[]{
                createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
                createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
                createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    }
}
