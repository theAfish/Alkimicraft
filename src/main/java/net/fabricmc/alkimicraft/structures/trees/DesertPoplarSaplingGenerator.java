package net.fabricmc.alkimicraft.structures.trees;

import net.fabricmc.alkimicraft.init.StructureInit;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DesertPoplarSaplingGenerator extends SaplingGenerator {
//    private final ConfiguredFeature<TreeFeatureConfig, ?> feature;

//    public DesertPoplarSaplingGenerator(ConfiguredFeature<?, ?> feature) {
//        this.feature = (ConfiguredFeature<TreeFeatureConfig, ?>) feature;
//    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return (ConfiguredFeature<TreeFeatureConfig, ?>) StructureInit.DESERT_POPLAR;
    }
}
