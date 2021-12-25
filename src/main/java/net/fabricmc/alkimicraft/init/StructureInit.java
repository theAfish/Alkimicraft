package net.fabricmc.alkimicraft.init;

import com.google.common.collect.ImmutableList;
import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.structures.DeadOakWoodsFeature;
import net.fabricmc.alkimicraft.structures.MushroomRockFeature;
import net.fabricmc.alkimicraft.structures.PillarFeature;
import net.fabricmc.alkimicraft.world.gen.AlterGroundSmall;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
//import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.*;
//import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class StructureInit {
    private static final Feature<DefaultFeatureConfig> DEAD_OAK_WOODS = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), new DeadOakWoodsFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> DEAD_OAK_WOODS_CONFIG = DEAD_OAK_WOODS.configure(FeatureConfig.DEFAULT);
    public static PlacedFeature DEAD_OAK_WOODS_PLACED_FEATURE = DEAD_OAK_WOODS_CONFIG.withPlacement(RarityFilterPlacementModifier.of(100), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE));

    private static final Feature<LakeFeature.Config> TOXIC_SEWAGE_LAKE = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), new LakeFeature(LakeFeature.Config.CODEC));
    public static final ConfiguredFeature<?, ?> TOXIC_SEWAGE_LAKE_CONFIG = TOXIC_SEWAGE_LAKE.configure(new LakeFeature.Config(BlockStateProvider.of(BlockInit.TOXIC_SEWAGE), BlockStateProvider.of(Blocks.SAND)));
    public static PlacedFeature TOXIC_SEWAGE_LAKE_PLACED_FEATURE = TOXIC_SEWAGE_LAKE_CONFIG.withPlacement(RarityFilterPlacementModifier.of(60), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE));

    public static final ConfiguredFeature<?, ?> DESERT_POPLAR = Feature.TREE
            // 使用builder配置特征地形
            .configure(new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LOG), // 树干方块提供器
                    new LargeOakTrunkPlacer(7, 3, 3), // 放置竖直树干
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LEAVES), // 树叶方块提供器
                    new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(2), 3), // 生成水滴状的树叶（半径、相对于树干的偏移、高度）
                    new TwoLayersFeatureSize(3, 0, 5) // 不同层的树木的宽度，用于查看树木在不卡到方块中可以有多高
            ).dirtProvider(BlockStateProvider.of(BlockInit.LOAMY_SAND))
                    .decorators(ImmutableList.of(new AlterGroundSmall(BlockStateProvider.of(BlockInit.LOAMY_SAND))))
                    .build());

    public static final ConfiguredFeature<?, ?> SMALL_DESERT_POPLAR = Feature.TREE
            // 使用builder配置特征地形
            .configure(new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LOG), // 树干方块提供器
                    new LargeOakTrunkPlacer(5, 2, 2), // 放置竖直树干
                    BlockStateProvider.of(BlockInit.DESERT_POPLAR_LEAVES), // 树叶方块提供器
                    new LargeOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(2), 2), // 生成水滴状的树叶（半径、相对于树干的偏移、高度）
                    new TwoLayersFeatureSize(3, 0, 5) // 不同层的树木的宽度，用于查看树木在不卡到方块中可以有多高
            ).dirtProvider(BlockStateProvider.of(BlockInit.LOAMY_SAND))
                    .decorators(ImmutableList.of(new AlterGroundSmall(BlockStateProvider.of(BlockInit.LOAMY_SAND))))
                    .build());

    public static final Feature<DefaultFeatureConfig> CHARRED_WOOD = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), new PillarFeature(DefaultFeatureConfig.CODEC, BlockInit.CHARRED_LOG.getDefaultState(), 3));
    public static final ConfiguredFeature<?, ?> CHARRED_WOOD_CONFIG = CHARRED_WOOD.configure(FeatureConfig.DEFAULT);
    public static PlacedFeature CHARRED_WOOD_PLACED_FEATURE = CHARRED_WOOD_CONFIG.withPlacement(CountMultilayerPlacementModifier.of(8), BiomePlacementModifier.of());

    private static final Feature<DefaultFeatureConfig> MUSHROOM_ROCK = Registry.register(Registry.FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), new MushroomRockFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> MUSHROOM_ROCK_CONFIG = MUSHROOM_ROCK.configure(new DefaultFeatureConfig());
    public static PlacedFeature MUSHROOM_ROCK_PLACED_FEATURE = MUSHROOM_ROCK_CONFIG.withPlacement(
            CountPlacementModifier.of(1), // number of veins per chunk
            SquarePlacementModifier.of(), // spreading horizontally
            HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE)); // height

    public static void init(){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), DEAD_OAK_WOODS_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods"), DEAD_OAK_WOODS_PLACED_FEATURE);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeInit.BARREN_DESERT_KEY), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "dead_oak_woods")));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), TOXIC_SEWAGE_LAKE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake"), TOXIC_SEWAGE_LAKE_PLACED_FEATURE);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeInit.BARREN_DESERT_KEY, BiomeInit.BARREN_BADLAND_KEY), GenerationStep.Feature.LAKES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_lake")));

        RegistryKey<ConfiguredFeature<?, ?>> desertPoplar = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, desertPoplar.getValue(), DESERT_POPLAR);
//        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, desertPoplar);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), CHARRED_WOOD_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "charred_wood"), CHARRED_WOOD_PLACED_FEATURE);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), MUSHROOM_ROCK_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(AlkimiCraft.MOD_ID, "mushroom_rock"), MUSHROOM_ROCK_PLACED_FEATURE);
    }
}
