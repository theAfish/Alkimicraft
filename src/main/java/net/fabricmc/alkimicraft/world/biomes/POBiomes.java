package net.fabricmc.alkimicraft.world.biomes;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.EndPlacedFeatures;

public class POBiomes {
    private static final RegistryKey<Biome> TEST_END_HIGHLANDS = RegistryKey.of(Registry.BIOME_KEY, new Identifier(AlkimiCraft.MOD_ID, "test_end_highlands"));
    public static final RegistryKey<Biome> GOBI = RegistryKey.of(Registry.BIOME_KEY, new Identifier(AlkimiCraft.MOD_ID,"gobi"));


    public static void init(){
        Registry.register(BuiltinRegistries.BIOME, TEST_END_HIGHLANDS.getValue(), createEndHighlands());

        register(GOBI, OverworldBiomeCreator.createBadlands(false));
    }

    private static Biome register(RegistryKey<Biome> key, Biome biome) {
        return Registry.register(BuiltinRegistries.BIOME, key, biome);
    }

    private static Biome createEndHighlands() {
        GenerationSettings.Builder builder = new GenerationSettings.Builder()
                .feature(GenerationStep.Feature.SURFACE_STRUCTURES, EndPlacedFeatures.END_GATEWAY_RETURN)
                .feature(GenerationStep.Feature.VEGETAL_DECORATION, EndPlacedFeatures.CHORUS_PLANT);
        return composeEndSpawnSettings(builder);
    }

    private static Biome composeEndSpawnSettings(GenerationSettings.Builder builder) {
        SpawnSettings.Builder builder2 = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addEndMobs(builder2);
        return (new Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.THEEND).temperature(0.5F).downfall(0.5F).effects((new BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(10518688).skyColor(0).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder2.build()).generationSettings(builder.build()).build();
    }

}
