package net.thenotfish.alkimicraft.utils;

import net.thenotfish.alkimicraft.blocks.LoggableFluidsEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.thenotfish.alkimicraft.blocks.BlockProperties;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class Defaults
{
    public static boolean tryFillWithFluid(Block block, WorldAccess worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
    {
        if (state.get(BlockProperties.fluidLogged) == LoggableFluidsEnum.EMPTY && !state.get(WATERLOGGED))
        {
            if (fluidStateIn.getFluid() == Fluids.WATER)
            {
                if (!worldIn.isClient())
                {
                    LoggableFluidsEnum fluid = LoggableFluidsEnum.getByFluid(fluidStateIn.getFluid(), fluidStateIn.getLevel());
                    worldIn.setBlockState(pos, state.with(BlockProperties.fluidLogged, fluid), 3);
                    worldIn.createAndScheduleFluidTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
                }

                return true;
            }
            else
            {
                if (!LoggableFluidsEnum.canFillWithFluid(block.getDefaultState(), fluidStateIn.getFluid()))
                    return false;
                LoggableFluidsEnum fluid = LoggableFluidsEnum.getByFluid(fluidStateIn.getFluid(), fluidStateIn.getLevel());
                if (fluid != LoggableFluidsEnum.EMPTY)
                {
                    if (!worldIn.isClient())
                    {
                        worldIn.setBlockState(pos, state.with(BlockProperties.fluidLogged, fluid), 3);
                        worldIn.createAndScheduleFluidTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public static boolean canFillWithFluid(Block it, BlockView worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
    {
        if(LoggableFluidsEnum.canFillWithFluid(it.getDefaultState(), fluidIn))
        {
            return state.get(BlockProperties.fluidLogged) == LoggableFluidsEnum.EMPTY;
        }
        return false;
//        else
//            return state.get(fluidLogged) == LoggableFluidsEnum.EMPTY && !state.get(WATERLOGGED) && LoggableFluidsEnum.getByFluid(fluidIn, 8).canFillWith(state, fluidIn);
    }

    public static ItemStack tryDrainFluid(Block it, WorldAccess world, BlockPos blockPos, BlockState blockState)
    {
        if (blockState.get(Properties.WATERLOGGED))
        {
            world.setBlockState(blockPos, blockState.with(Properties.WATERLOGGED, false), 3);
            if (!blockState.canPlaceAt(world, blockPos))
            {
                world.breakBlock(blockPos, true);
            }

            return new ItemStack(Items.WATER_BUCKET);
        }
        else if (blockState.get(BlockProperties.fluidLogged).height == 8)
        {
            LoggableFluidsEnum fluidsEnum = blockState.get(BlockProperties.fluidLogged);
            world.setBlockState(blockPos, blockState.with(Properties.WATERLOGGED, false).with(BlockProperties.fluidLogged, LoggableFluidsEnum.EMPTY), 3);
            if (!blockState.canPlaceAt(world, blockPos))
            {
                world.breakBlock(blockPos, true);
            }
            return fluidsEnum.getBucket();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void getPlacementState(Block block, ItemPlacementContext context, CallbackInfoReturnable<BlockState> cir)
    {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        if (ifluidstate.getFluid() == Fluids.WATER)
            cir.setReturnValue(cir.getReturnValue().with(BlockProperties.fluidLogged, LoggableFluidsEnum.WATER));
        else if (LoggableFluidsEnum.canFillWithFluid(block.getDefaultState(), ifluidstate.getFluid()))
            cir.setReturnValue(cir.getReturnValue().with(WATERLOGGED, false).with(BlockProperties.fluidLogged, LoggableFluidsEnum.getByFluid(ifluidstate.getFluid(), ifluidstate.getLevel())));
        else
            cir.setReturnValue(cir.getReturnValue().with(WATERLOGGED, false).with(BlockProperties.fluidLogged, LoggableFluidsEnum.EMPTY));
    }

    public static FluidState getFluidState(BlockState state)
    {
        if (state.get(Properties.WATERLOGGED))
            return Fluids.WATER.getStill(false);
        else
        {
            LoggableFluidsEnum fluid = state.get(BlockProperties.fluidLogged);
            if (fluid.contains.isEmpty())
                return Fluids.EMPTY.getDefaultState();
            else if (fluid.height == 8)
                return state.get(BlockProperties.fluidLogged).contains.get().getStill( false);
            else
                return state.get(BlockProperties.fluidLogged).contains.get().getFlowing(state.get(BlockProperties.fluidLogged).height, false);
        }
    }

    public static void getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, WorldAccess worldIn, BlockPos currentPos, BlockPos facingPos, CallbackInfoReturnable<BlockState> ci)
    {
        if (LoggableFluidsEnum.canFillWithFluid(stateIn, worldIn.getFluidState(currentPos).getFluid())) {
            stateIn.get(BlockProperties.fluidLogged).contains.ifPresent(FlowableFluid -> worldIn.createAndScheduleFluidTick(currentPos, FlowableFluid, FlowableFluid.getTickRate(worldIn)));
        }
    }
}