package net.fabricmc.alkimicraft.utils;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class BlockRenders {

    public static void defineRenders() {

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.BLOCK_REED, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.BLOCK_REED);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DESERT_POPLAR_SAPLING, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.DESERT_POPLAR_SAPLING);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.ARTEMISIA_ARENARIA, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.ARTEMISIA_ARENARIA);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.IRON_ROOT_GRASS, RenderLayer.getCutoutMipped());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xFFFFFF, BlockInit.IRON_ROOT_GRASS);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SEA_BUCKTHORN, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.DESERT_POPLAR_LEAVES, RenderLayer.getCutoutMipped());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0xC8FF46, BlockInit.DESERT_POPLAR_LEAVES);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.PENCIL_PLANT_TOP, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.FERTILE_BEANS, RenderLayer.getCutoutMipped());

    }
}
