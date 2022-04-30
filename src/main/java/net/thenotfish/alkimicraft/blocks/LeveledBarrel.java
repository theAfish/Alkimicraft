package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Map;
import java.util.function.Predicate;

public class LeveledBarrel extends AbstractWoodenBarrel{
    public static final IntProperty LEVEL;
    public static final Predicate<Biome.Precipitation> RAIN_PREDICATE;
    public static final Predicate<Biome.Precipitation> SNOW_PREDICATE;
    private final Predicate<Biome.Precipitation> precipitationPredicate;

    public LeveledBarrel(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, behaviorMap);
        this.precipitationPredicate = precipitationPredicate;
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 1));
    }

    public boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
    }

    protected boolean canBeFilledByDripstone(Fluid fluid) {
        return fluid == Fluids.WATER && this.precipitationPredicate == RAIN_PREDICATE;
    }

    protected double getFluidHeight(BlockState state) {
        return (6.0D + (double)state.get(LEVEL) * 3.0D) / 16.0D;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity.isOnFire() && this.isEntityTouchingFluid(state, pos, entity)) {
            entity.extinguish();
            if (entity.canModifyAt(world, pos)) {
                this.onFireCollision(state, world, pos);
            }
        }
    }

    protected void onFireCollision(BlockState state, World world, BlockPos pos) {
        decrementFluidLevel(state, world, pos);
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(LEVEL) - 1;
        world.setBlockState(pos, i == 0 ? BlockInit.WOODEN_BARREL.getDefaultState() : state.with(LEVEL, i));
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos, int level) {
        int i = state.get(LEVEL) - level;
        if (i >= 0){
            world.setBlockState(pos, i == 0 ? BlockInit.WOODEN_BARREL.getDefaultState() : state.with(LEVEL, i));
        }
    }

    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (WoodenBarrel.canFillWithPrecipitation(world, precipitation) && state.get(LEVEL) != 3 && this.precipitationPredicate.test(precipitation)) {
            world.setBlockState(pos, state.cycle(LEVEL));
        }
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return (Integer)state.get(LEVEL);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
        if (!this.isFull(state)) {
            world.setBlockState(pos, state.with(LEVEL, state.get(LEVEL) + 1));
            world.syncWorldEvent(1047, pos, 0);
        }
    }

    static {
        LEVEL = Properties.LEVEL_3;
        RAIN_PREDICATE = (precipitation) -> {
            return precipitation == Biome.Precipitation.RAIN;
        };
        SNOW_PREDICATE = (precipitation) -> {
            return precipitation == Biome.Precipitation.SNOW;
        };
    }
}
