package net.fabricmc.alkimicraft.utils;

import net.fabricmc.alkimicraft.client.renderer.block.entity.PackingTableBlockEntityRenderer;
import net.fabricmc.alkimicraft.client.renderer.block.entity.TipiFireEntityRenderer;
import net.fabricmc.alkimicraft.init.BlockEntityInit;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;


public class BlockEntityRenders {
    public static void defineRenders() {
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityInit.TIPI_FIRE_ENTITY, TipiFireEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityInit.PACKING_TABLE_BLOCK_ENTITY, PackingTableBlockEntityRenderer::new);
    }

}
