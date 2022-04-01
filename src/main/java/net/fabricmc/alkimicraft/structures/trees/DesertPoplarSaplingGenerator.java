package net.fabricmc.alkimicraft.structures.trees;

import net.fabricmc.alkimicraft.init.StructureInit;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.Random;

public class DesertPoplarSaplingGenerator extends SaplingGenerator {
//    private final ConfiguredFeature<TreeFeatureConfig, ?> feature;

//    public DesertPoplarSaplingGenerator(ConfiguredFeature<?, ?> feature) {
//        this.feature = (ConfiguredFeature<TreeFeatureConfig, ?>) feature;
//    }

    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
//        return (ConfiguredFeature<TreeFeatureConfig, ?>) StructureInit.DESERT_POPLAR;
        return RegistryEntry.of(StructureInit.DESERT_POPLAR);
    }
}
