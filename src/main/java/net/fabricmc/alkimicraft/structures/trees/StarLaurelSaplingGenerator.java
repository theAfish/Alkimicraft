package net.fabricmc.alkimicraft.structures.trees;

import net.fabricmc.alkimicraft.init.StructureInit;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class StarLaurelSaplingGenerator extends SaplingGenerator {

    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(StructureInit.STAR_LAUREL);
    }
}
