package net.fabricmc.alkimicraft.utils;

public class WorldSeedHolder {
    /**
     * Copied from https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/blob/latest-released/src/main/java/com/telepathicgrunt/ultraamplifieddimension/utils/WorldSeedHolder.java
     * World seed for worldgen when not specified by JSON by Haven King
     * https://github.com/Hephaestus-Dev/seedy-behavior/blob/master/src/main/java/dev/hephaestus/seedy/mixin/world/gen/GeneratorOptionsMixin.java
     */
    private static long SEED = 0;

    public static long getSeed() {
        return SEED;
    }

    public static long setSeed(long seed) {
        SEED = seed;
        return seed;
    }
}