package net.thenotfish.alkimicraft.utils;

import net.thenotfish.alkimicraft.client.renderer.block.entity.PackingTableBlockEntityRenderer;
import net.thenotfish.alkimicraft.client.renderer.block.entity.TipiFireEntityRenderer;
import net.thenotfish.alkimicraft.init.BlockEntityInit;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;


public class BlockEntityRenders {
    public static void defineRenders() {
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityInit.TIPI_FIRE_ENTITY, TipiFireEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityInit.PACKING_TABLE_BLOCK_ENTITY, PackingTableBlockEntityRenderer::new);
    }

}
