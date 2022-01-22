package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.blocks.entities.WoodenBarrelEntity;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Predicate;


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
