package net.fabricmc.alkimicraft.world.biomes;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class AlkimicraftBiomeKeys {

    public static final RegistryKey<Biome> GOBI = register("gobi");

    public static void init(){

    }

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(AlkimiCraft.MOD_ID,name));
    }
}
