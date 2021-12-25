package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;

public class BiomeInit {

    public static final RegistryKey<Biome> BARREN_DESERT_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(AlkimiCraft.MOD_ID, "barren_desert"));
    public static final RegistryKey<Biome> BARREN_BADLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(AlkimiCraft.MOD_ID, "barren_badland"));

}
