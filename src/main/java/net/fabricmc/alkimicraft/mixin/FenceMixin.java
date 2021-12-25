package net.fabricmc.alkimicraft.mixin;

import net.fabricmc.alkimicraft.utils.Defaults;
import net.fabricmc.alkimicraft.blocks.IFluidLoggable;
import net.fabricmc.alkimicraft.blocks.LoggableFluidsEnum;
import static net.fabricmc.alkimicraft.blocks.BlockProperties.fluidLogged;

import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(FenceBlock.class)
public abstract class FenceMixin extends Block implements Waterloggable, IFluidLoggable
{
    public FenceMixin(AbstractBlock.Settings properties)
    {
        super(properties);
    }


    @Override
    public ItemStack tryDrainFluid(WorldAccess worldIn, BlockPos pos, BlockState state)
    {
        return Defaults.tryDrainFluid(this, worldIn, pos, state);
    }

    @Override
    public boolean canFillWithFluid(BlockView worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
    {
        return Defaults.canFillWithFluid(this, worldIn, pos, state, fluidIn);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
    {
        return Defaults.tryFillWithFluid(this, worldIn, pos, state, fluidStateIn);
    }

    @Inject(method = "getPlacementState", cancellable = true, at = @At("RETURN"))
    public void getPlacementState(ItemPlacementContext context, CallbackInfoReturnable<BlockState> ci)
    {
        Defaults.getPlacementState(this, context, ci);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return Defaults.getFluidState(state);
    }


    @Inject(method = "getStateForNeighborUpdate", cancellable = true, at = @At("RETURN"))
    public void getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, WorldAccess worldIn, BlockPos currentPos, BlockPos facingPos, CallbackInfoReturnable<BlockState> ci)
    {
        Defaults.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos, ci);
    }
}