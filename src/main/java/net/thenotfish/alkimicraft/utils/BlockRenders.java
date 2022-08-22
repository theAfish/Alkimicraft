package net.thenotfish.alkimicraft.utils;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

public class BlockRenders {

    public static void defineRenders() {

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.BLOCK_REED, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.BLOCK_REED);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SNOW_LANTERN_PLANT, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.SNOW_LANTERN_PLANT);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.FOREVER_BRIGHT_SNOW_LANTERN_PLANT, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DESERT_POPLAR_SAPLING, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.DESERT_POPLAR_SAPLING);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ARTEMISIA_ARENARIA, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.ARTEMISIA_ARENARIA);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_ROOT_GRASS, RenderLayer.getCutoutMipped());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.IRON_ROOT_GRASS);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SEA_BUCKTHORN, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DESERT_POPLAR_LEAVES, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFD21B, BlockInit.DESERT_POPLAR_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STAR_LAUREL_LEAVES, RenderLayer.getCutoutMipped());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3a5d1d, BlockInit.STAR_LAUREL_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.JUJUBE_LEAVES, RenderLayer.getCutoutMipped());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x64D007, BlockInit.JUJUBE_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.JUJUBE_TOP, RenderLayer.getCutoutMipped());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x64D007, BlockInit.JUJUBE_TOP);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PENCIL_PLANT_TOP, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.FERTILE_BEANS, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.WOODEN_BARREL_WATER, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getWaterColor(view, pos), BlockInit.WOODEN_BARREL_WATER);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.MICROSCOPE, RenderLayer.getCutoutMipped());

//        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.WOODEN_BARREL_SEWAGE, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xB98747, BlockInit.WOODEN_BARREL_SEWAGE);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SMALL_GOLD_BUD, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.MEDIUM_GOLD_BUD, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.LARGE_GOLD_BUD, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.GOLD_CLUSTER, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.COPPER_ELSHOLTZIA, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.COPPER_ELSHOLTZIA);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STAR_LAUREL_TRAPDOOR, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STAR_LAUREL_DOOR, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STAR_LAUREL_SAPLING, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.STAR_LAUREL_SEEDLING, RenderLayer.getCutoutMipped());

    }
}
