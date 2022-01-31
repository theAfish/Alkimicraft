package net.fabricmc.alkimicraft.mixin;

import com.google.common.base.MoreObjects;
import net.fabricmc.alkimicraft.utils.WorldSeedHolder;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Properties;
import java.util.Random;

// This one was copied from https://github.com/SuperCoder7979/survivalisland/blob/master/src/main/java/supercoder79/survivalisland/mixin/MixinGeneratorOptions.java

@Mixin(GeneratorOptions.class)
public class MixinGeneratorOptions {
    /**
     * World seed for worldgen when not specified by JSON by Haven King
     * https://github.com/Hephaestus-Dev/seedy-behavior/blob/master/src/main/java/dev/hephaestus/seedy/mixin/world/gen/GeneratorOptionsMixin.java
     */
    @Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
            at = @At(value = "RETURN"))
    private void uad_giveUsRandomSeeds(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> simpleRegistry, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        WorldSeedHolder.setSeed(seed);
    }
}