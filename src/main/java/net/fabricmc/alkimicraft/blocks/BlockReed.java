package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.FluidInit;
import net.fabricmc.alkimicraft.utils.Defaults;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.PropertyPermission;
import java.util.Random;

public class BlockReed extends PlantBlock implements Waterloggable, IFluidLoggable {
    public static final IntProperty AGE;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<LoggableFluidsEnum> FLUIDLOGGED;
    protected static final VoxelShape SHAPE;

    public BlockReed(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(AGE, 0)).with(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.getFluid() == FluidInit.TOXIC_SEWAGE_STILL){
            return super.getPlacementState(ctx).with(FLUIDLOGGED, LoggableFluidsEnum.getByFluid(FluidInit.TOXIC_SEWAGE_STILL, fluidState.getLevel()));
        }
        return super.getPlacementState(ctx).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return Defaults.canFillWithFluid(this, world, pos, state, fluid);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean)state.get(Properties.WATERLOGGED) && fluidState.getFluid() == FluidInit.TOXIC_SEWAGE_STILL) {
            if (!world.isClient()) {
                world.setBlockState(pos, (BlockState)state.with(Properties.WATERLOGGED, true), Block.NOTIFY_ALL);
                world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        } else {
            return false;
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isAir(pos.up())) {
            int i;
            for(i = 1; world.getBlockState(pos.down(i)).isOf(this); ++i) {}

            if (i < 3) {
                int j = state.get(AGE);
                if (j == 15) {
                    world.setBlockState(pos.up(), this.getDefaultState().with(WATERLOGGED, false));
                    world.setBlockState(pos, state.with(AGE, 0), Block.NO_REDRAW);
                } else {
                    world.setBlockState(pos, state.with(AGE, j + 1), Block.NO_REDRAW);
                }
            }else {
                if (!world.getBlockState(pos.down(i-1)).get(WATERLOGGED)) {
                    world.setBlockState(pos.down(i - 1), this.getDefaultState().with(FLUIDLOGGED, LoggableFluidsEnum.WATER));
                }
            }
        }

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (state.get(WATERLOGGED)) {
                world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public FluidState getFluidState(BlockState state) {
        return Defaults.getFluidState(state);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(BlockInit.BLOCK_REED)) {
            return true;
        } else {
            if (blockState.isIn(BlockTags.DIRT) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.RED_SAND)) {
                FluidState fluidState = world.getFluidState(pos);
                FluidState topFluidState = world.getFluidState(pos.up());
                return fluidState.isIn(FluidTags.WATER) && !topFluidState.isIn(FluidTags.WATER);
            }
            return false;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);
    }

    static {
        AGE = Properties.AGE_15;
        WATERLOGGED = Properties.WATERLOGGED;
        FLUIDLOGGED = BlockProperties.fluidLogged;
        SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    }
}

