package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;


public class WoodenBarrel extends AbstractWoodenBarrel {

    public WoodenBarrel(Settings settings) {
        super(settings, BlockInit.EMPTY_BARREL_BEHAVIOR);
    }

    @Override
    public boolean isFull(BlockState state) {
        return false;
    }

    public static boolean canFillWithPrecipitation(World world, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return world.getRandom().nextFloat() < 0.1F;
        } else {
            return false;
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (canFillWithPrecipitation(world, precipitation)) {
            if (precipitation == Biome.Precipitation.RAIN) {
                world.setBlockState(pos, BlockInit.WOODEN_BARREL_WATER.getDefaultState());
                world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
            }
        }
    }

    protected boolean canBeFilledByDripstone(Fluid fluid) {
        return false;
    }
}
