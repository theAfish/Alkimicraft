package net.thenotfish.alkimicraft.world.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class POBiomeCreator extends OverworldBiomeCreator {
    protected static final int DEFAULT_WATER_COLOR = 4159204;
    protected static final int DEFAULT_WATER_FOG_COLOR = 329011;
    private static final int DEFAULT_FOG_COLOR = 12638463;

    @Nullable
    private static final MusicSound DEFAULT_MUSIC = null;

    public POBiomeCreator() {
        super();
    }

    private static void addBasicFeatures(net.minecraft.world.biome.GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    public static Biome createGobi(boolean plateau) {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        net.minecraft.world.biome.GenerationSettings.Builder builder2 = new net.minecraft.world.biome.GenerationSettings.Builder();
        addBasicFeatures(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addExtraGoldOre(builder2);
//        DefaultBiomeFeatures.addDefaultDisks(builder2);
        if (plateau) {
            DefaultBiomeFeatures.addBadlandsPlateauTrees(builder2);
        }

        DefaultBiomeFeatures.addBadlandsGrass(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addBadlandsVegetation(builder2);
        return (new net.minecraft.world.biome.Biome.Builder()).precipitation(Biome.Precipitation.NONE).category(Biome.Category.MESA).temperature(2.0F).downfall(0.0F).effects((new net.minecraft.world.biome.BiomeEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(2.0F)).foliageColor(10387789).grassColor(9470285).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(builder.build()).generationSettings(builder2.build()).build();
    }
}
