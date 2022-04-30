package net.thenotfish.alkimicraft.init;

import com.google.common.collect.ImmutableList;
import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.structures.*;
import net.thenotfish.alkimicraft.world.gen.AlterGroundSmall;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.thenotfish.alkimicraft.structures.*;

import java.util.Arrays;

public class StructureInit {
    private static final Feature<DefaultFeatureConfig> DEAD_OAK_WOODS = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), new DeadOakWoodsFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> DEAD_OAK_WOODS_CONFIG = new ConfiguredFeature<>(DEAD_OAK_WOODS,FeatureConfig.DEFAULT);
    public static PlacedFeature DEAD_OAK_WOODS_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DEAD_OAK_WOODS_CONFIG), Arrays.asList(RarityFilterPlacementModifier.of(100), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE)));

    private static final Feature<DefaultFeatureConfig> IRON_ROOT = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "iron_root"), new IronRootFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> IRON_ROOT_CONFIG = new ConfiguredFeature<>(IRON_ROOT,FeatureConfig.DEFAULT);
    public static PlacedFeature IRON_ROOT_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(IRON_ROOT_CONFIG), Arrays.asList(CountPlacementModifier.of(20), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))));

    private static final Feature<LakeFeature.Config> TOXIC_SEWAGE_LAKE = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), new LakeFeature(LakeFeature.Config.CODEC));
    public static final ConfiguredFeature<?, ?> TOXIC_SEWAGE_LAKE_CONFIG = new ConfiguredFeature<>(TOXIC_SEWAGE_LAKE, new LakeFeature.Config(BlockStateProvider.of(BlockInit.TOXIC_SEWAGE), BlockStateProvider.of(Blocks.SAND)));
    public static PlacedFeature TOXIC_SEWAGE_LAKE_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(TOXIC_SEWAGE_LAKE_CONFIG), Arrays.asList(RarityFilterPlacementModifier.of(60), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE)));

    private static final Feature<RandomBarrierLakeFeature.Config> LAVA_LAKE_WITH_DEPOSIT = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "lava_lake_with_deposit"), new RandomBarrierLakeFeature(RandomBarrierLakeFeature.Config.CODEC));
    public static final ConfiguredFeature<?, ?> LAVA_LAKE_WITH_DEPOSIT_CONFIG = new ConfiguredFeature<>(LAVA_LAKE_WITH_DEPOSIT, new RandomBarrierLakeFeature.Config(BlockStateProvider.of(Blocks.LAVA), BlockStateProvider.of(Blocks.BLACKSTONE), BlockStateProvider.of(BlockInit.GOLD_ORE_DEPOSIT)));
    public static PlacedFeature LAVA_LAKE_WITH_DEPOSIT_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(LAVA_LAKE_WITH_DEPOSIT_CONFIG), Arrays.asList(RarityFilterPlacementModifier.of(100), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE)));

    public static final Feature<DefaultFeatureConfig> JUJUBE_TREE = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "jujube_tree"), new JujubeTreeFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> JUJUBE_TREE_CONFIG = new ConfiguredFeature<>(JUJUBE_TREE, FeatureConfig.DEFAULT);
    public static PlacedFeature JUJUBE_TREE_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(JUJUBE_TREE_CONFIG), Arrays.asList(CountPlacementModifier.of(UniformIntProvider.create(0, 1)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

    public static final Feature<DefaultFeatureConfig> PENCIL_PLANT = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant"), new PencilPlantFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> PENCIL_PLANT_CONFIG = new ConfiguredFeature<>(PENCIL_PLANT, FeatureConfig.DEFAULT);
    public static PlacedFeature PENCIL_PLANT_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(PENCIL_PLANT_CONFIG), Arrays.asList(NoiseThresholdCountPlacementModifier.of(0.8,0,2),SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));


    public static final ConfiguredFeature<?, ?> COPPER_ELSHOLTZIA = new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchFeatureConfig(64,6,3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.COPPER_ELSHOLTZIA.getDefaultState().with(Properties.AGE_7, 7))))));
    public static PlacedFeature COPPER_ELSHOLTZIA_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(COPPER_ELSHOLTZIA), Arrays.asList(RarityFilterPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));


    public static final ConfiguredFeature<?, ?> SEA_BUCKTHORN = new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchFeatureConfig(32,4,3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockInit.SEA_BUCKTHORN.getDefaultState().with(Properties.AGE_5, 5))))));
    public static PlacedFeature SEA_BUCKTHORN_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(SEA_BUCKTHORN), Arrays.asList(RarityFilterPlacementModifier.of(20), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));

    public static final ConfiguredFeature<?, ?> DESERT_POPLAR = new ConfiguredFeature<>(Feature.TREE,
            // 使用builder配置特征地形
            new TreeFeatureConfig(
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LOG), // 树干方块提供器
                    new LargeOakTrunkPlacer(6, 3, 3), // 放置竖直树干
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LEAVES), // 树叶方块提供器
                    new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(2), 3), // 生成水滴状的树叶（半径、相对于树干的偏移、高度）
                    BlockStateProvider.of(BlockInit.LOAMY_SAND), // 树叶方块提供器
                    new TwoLayersFeatureSize(3, 0, 5), // 不同层的树木的宽度，用于查看树木在不卡到方块中可以有多高
                    ImmutableList.of(new AlterGroundSmall(BlockStateProvider.of(BlockInit.LOAMY_SAND))),
                    true,true
            ){});
    public static PlacedFeature DESERT_POPLAR_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(DESERT_POPLAR), Arrays.asList(RarityFilterPlacementModifier.of(300), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));

    public static final ConfiguredFeature<?, ?> STAR_LAUREL = new ConfiguredFeature<>(Feature.TREE,
            // 使用builder配置特征地形
            new TreeFeatureConfig(
                    BlockStateProvider.of(BlockInit.STAR_LAUREL_LOG), // 树干方块提供器
                    new StraightTrunkPlacer(4, 1, 1), // 放置竖直树干
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(BlockInit.STAR_LAUREL_LEAVES.getDefaultState(), 3).add(BlockInit.FLOWERING_STAR_LAUREL_LEAVES.getDefaultState(), 1).build()), // 树叶方块提供器
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    BlockStateProvider.of(Blocks.DIRT),
                    new TwoLayersFeatureSize(1, 0, 1), // 不同层的树木的宽度，用于查看树木在不卡到方块中可以有多高
                    ImmutableList.of(),
                    true,true
            ){});
    public static PlacedFeature STAR_LAUREL_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(STAR_LAUREL), Arrays.asList(RarityFilterPlacementModifier.of(1),PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP));

    private static final Feature<DefaultFeatureConfig> LAUREL_WITH_SNOW_LANTERN = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "laurel_with_snow_lantern"), new LaurelAndLanternFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> LAUREL_WITH_SNOW_LANTERN_CONFIG = new ConfiguredFeature<>(LAUREL_WITH_SNOW_LANTERN,FeatureConfig.DEFAULT);
    public static PlacedFeature LAUREL_WITH_SNOW_LANTERN_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(LAUREL_WITH_SNOW_LANTERN_CONFIG), Arrays.asList(RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));


//    public static final ConfiguredFeature<?, ?> SMALL_DESERT_POPLAR = Feature.TREE
//            // 使用builder配置特征地形
//            .configure(new TreeFeatureConfig.Builder(
//                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LOG), // 树干方块提供器
//                    new LargeOakTrunkPlacer(5, 2, 2), // 放置竖直树干
//                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LEAVES), // 树叶方块提供器
//                    new LargeOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(2), 2), // 生成水滴状的树叶（半径、相对于树干的偏移、高度）
//                    new TwoLayersFeatureSize(3, 0, 5) // 不同层的树木的宽度，用于查看树木在不卡到方块中可以有多高
//            ).dirtProvider(BlockStateProvider.of(BlockInit.LOAMY_SAND))
//                    .decorators(ImmutableList.of(new AlterGroundSmall(BlockStateProvider.of(BlockInit.LOAMY_SAND))))
//                    .build());

    public static final Feature<DefaultFeatureConfig> CHARRED_WOOD = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), new PillarFeature(DefaultFeatureConfig.CODEC, BlockInit.CHARRED_LOG.getDefaultState(), 3));
    public static final ConfiguredFeature<?, ?> CHARRED_WOOD_CONFIG = new ConfiguredFeature<>(CHARRED_WOOD, FeatureConfig.DEFAULT);
    public static PlacedFeature CHARRED_WOOD_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(CHARRED_WOOD_CONFIG), Arrays.asList(CountMultilayerPlacementModifier.of(8), BiomePlacementModifier.of()));

    private static final Feature<DefaultFeatureConfig> MUSHROOM_ROCK = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), new MushroomRockFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> MUSHROOM_ROCK_CONFIG = new ConfiguredFeature<>(MUSHROOM_ROCK,new DefaultFeatureConfig());
    public static PlacedFeature MUSHROOM_ROCK_PLACED_FEATURE = new PlacedFeature(RegistryEntry.of(MUSHROOM_ROCK_CONFIG), Arrays.asList(
            CountPlacementModifier.of(1), // number of veins per chunk
            SquarePlacementModifier.of(), // spreading horizontally
            HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE))); // height

    public static void init(){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), DEAD_OAK_WOODS_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), DEAD_OAK_WOODS_PLACED_FEATURE);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeInit.BARREN_DESERT_KEY), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods")));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "iron_root"), IRON_ROOT_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "iron_root"), IRON_ROOT_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "iron_root")));


        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), TOXIC_SEWAGE_LAKE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), TOXIC_SEWAGE_LAKE_PLACED_FEATURE);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeInit.BARREN_DESERT_KEY, BiomeInit.BARREN_BADLAND_KEY), GenerationStep.Feature.LAKES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake")));

        RegistryKey<ConfiguredFeature<?, ?>> desertPoplar = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, desertPoplar.getValue(), DESERT_POPLAR);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar"), DESERT_POPLAR_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(AlkimiCraft.MOD_ID, "desert_poplar")));

        RegistryKey<ConfiguredFeature<?, ?>> star_laurel = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "star_laurel"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, star_laurel.getValue(), LAUREL_WITH_SNOW_LANTERN_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "star_laurel"), LAUREL_WITH_SNOW_LANTERN_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(AlkimiCraft.MOD_ID, "star_laurel")));


        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "jujube_tree"), JUJUBE_TREE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "jujube_tree"), JUJUBE_TREE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST, BiomeKeys.WINDSWEPT_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(AlkimiCraft.MOD_ID, "jujube_tree")));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant"), PENCIL_PLANT_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "pencil_plant"), PENCIL_PLANT_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(AlkimiCraft.MOD_ID, "pencil_plant")));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), CHARRED_WOOD_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), CHARRED_WOOD_PLACED_FEATURE);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), MUSHROOM_ROCK_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), MUSHROOM_ROCK_PLACED_FEATURE);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "lava_lake_with_deposit"), LAVA_LAKE_WITH_DEPOSIT_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "lava_lake_with_deposit"), LAVA_LAKE_WITH_DEPOSIT_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BADLANDS, BiomeKeys.WOODED_BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.DESERT), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(AlkimiCraft.MOD_ID, "lava_lake_with_deposit")));

        RegistryKey<ConfiguredFeature<?, ?>> copperElsholtzia = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "copper_elsholtzia"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, copperElsholtzia.getValue(), COPPER_ELSHOLTZIA);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "copper_elsholtzia"), COPPER_ELSHOLTZIA_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(AlkimiCraft.MOD_ID, "copper_elsholtzia")));

        RegistryKey<ConfiguredFeature<?, ?>> seaBuckthorn = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, seaBuckthorn.getValue(), SEA_BUCKTHORN);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn"), SEA_BUCKTHORN_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(AlkimiCraft.MOD_ID, "sea_buckthorn")));
    }
}
