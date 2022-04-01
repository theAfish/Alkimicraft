package net.fabricmc.alkimicraft.world.biomes;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.OverworldBiomeCreator;

public class POBiomes {

    public static void init(){}

    private static Biome register(RegistryKey<Biome> key, Biome biome) {
        return Registry.register(BuiltinRegistries.BIOME, key, biome);
    }

    static {
        register(AlkimicraftBiomeKeys.GOBI, POBiomeCreator.createBadlands(false));
    }
}
