//package net.fabricmc.alkimicraft.fluid;
//
//import net.fabricmc.alkimicraft.init.BlockInit;
//import net.fabricmc.alkimicraft.init.FluidInit;
//import net.fabricmc.alkimicraft.init.ItemInit;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.FluidBlock;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.fluid.FluidState;
//import net.minecraft.fluid.WaterFluid;
//import net.minecraft.item.Item;
//import net.minecraft.particle.ParticleTypes;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.state.StateManager;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldView;
//
//import java.util.Random;
//
//public class FluidSaltyWater extends FluidSewage {
//
//    @Override
//    public Fluid getStill()
//    {
//        return FluidInit.SALTY_WATER_STILL;
//    }
//
//    @Override
//    public Fluid getFlowing()
//    {
//        return FluidInit.SALTY_WATER_FLOW;
//    }
//
//    @Override
//    public Item getBucketItem()
//    {
//        return ItemInit.SALTY_WATER_BUCKET;
//    }
//
//    @Override
//    public BlockState toBlockState(FluidState fluidState)
//    {
//        return BlockInit.SALTY_WATER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(fluidState));
//    }
//
//    @Override
//    protected int getFlowSpeed(WorldView world) {
//        return 4;
//    }
//
//    @Override
//    protected int getLevelDecreasePerBlock(WorldView world) {
//        return 1;
//    }
//
//    @Override
//    public int getTickRate(WorldView world) {
//        return 5;
//    }
//
//    @Override
//    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
//        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
//            if (random.nextInt(64) == 0) {
//                world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
//            }
//        } else if (random.nextInt(10) == 0) {
//            world.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
//        }
//    }
//
//
//    @Override
//    public boolean isStill(FluidState state) {
//        return false;
//    }
//
//    @Override
//    public int getLevel(FluidState state) {
//        return 0;
//    }
//
//    public static class Flowing extends FluidSaltyWater
//    {
//        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
//        {
//            super.appendProperties(builder);
//            builder.add(LEVEL);
//        }
//
//        public int getLevel(FluidState fluidState)
//        {
//            return fluidState.get(LEVEL);
//        }
//
//        public boolean isStill(FluidState fluidState)
//        {
//            return false;
//        }
//    }
//
//    public static class Still extends FluidSaltyWater
//    {
//        public int getLevel(FluidState fluidState)
//        {
//            return 8;
//        }
//
//        public boolean isStill(FluidState fluidState)
//        {
//            return true;
//        }
//    }
//}
