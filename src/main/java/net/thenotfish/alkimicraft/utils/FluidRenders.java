package net.thenotfish.alkimicraft.utils;

import net.thenotfish.alkimicraft.init.FluidInit;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.function.Function;

public class FluidRenders {
    public static void defineRenders() {
        setupFluidRendering(FluidInit.TOXIC_SEWAGE_STILL, FluidInit.TOXIC_SEWAGE_FLOW, new Identifier("alkimicraft", "toxic_sewage"), 0x8A8A6D);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), FluidInit.TOXIC_SEWAGE_STILL, FluidInit.TOXIC_SEWAGE_FLOW);

//        setupFluidRendering(FluidInit.SALTY_WATER_STILL, FluidInit.SALTY_WATER_FLOW, new Identifier("minecraft", "water"), 0x38CAED); // 0x38CAED
//        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), FluidInit.SALTY_WATER_STILL, FluidInit.SALTY_WATER_FLOW);
//
//        setupFluidRendering(FluidInit.FRESH_WATER_STILL, FluidInit.FRESH_WATER_FLOW, new Identifier("minecraft", "water"), 0x0EA0E4);
//        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), FluidInit.FRESH_WATER_STILL, FluidInit.FRESH_WATER_FLOW);
    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlastTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }

        });

        final FluidRenderHandler renderHandler = new FluidRenderHandler() {

            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }

}
