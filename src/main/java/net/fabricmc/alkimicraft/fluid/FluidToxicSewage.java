package net.fabricmc.alkimicraft.fluid;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.FluidInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;


public abstract class FluidToxicSewage extends FluidSewage { //FlowableFluid {
    @Override
    public Fluid getStill()
    {
        return FluidInit.TOXIC_SEWAGE_STILL;
    }

    @Override
    public Fluid getFlowing()
    {
        return FluidInit.TOXIC_SEWAGE_FLOW;
    }

    @Override
    public Item getBucketItem()
    {
        return ItemInit.TOXIC_SEWAGE_BUCKET;
    }

//    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
//        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
//    }

    @Override
    protected BlockState toBlockState(FluidState fluidState)
    {
        // method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        // method_15741 may be getBlockStateLevel
        return BlockInit.TOXIC_SEWAGE.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends FluidToxicSewage
    {
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
        {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        public int getLevel(FluidState fluidState)
        {
            return fluidState.get(LEVEL);
        }

        public boolean isStill(FluidState fluidState)
        {
            return false;
        }
    }

    public static class Still extends FluidToxicSewage
    {
        public int getLevel(FluidState fluidState)
        {
            return 8;
        }

        public boolean isStill(FluidState fluidState)
        {
            return true;
        }
    }
}
