package net.fabricmc.alkimicraft;

import net.fabricmc.alkimicraft.blocks.entities.TipiFireEntity;
import net.fabricmc.alkimicraft.client.model.StarryBodyEntityModel;
import net.fabricmc.alkimicraft.client.renderer.StarryBodyEntityRenderer;
import net.fabricmc.alkimicraft.client.renderer.block.entity.TipiFireEntityRenderer;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.EntityInit;
import net.fabricmc.alkimicraft.utils.BlockEntityRenders;
import net.fabricmc.alkimicraft.utils.BlockRenders;
import net.fabricmc.alkimicraft.utils.FluidRenders;
import net.fabricmc.alkimicraft.utils.ItemRenders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AlkimiCraftClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_STARRY_BODY_LAYER = new EntityModelLayer(new Identifier(AlkimiCraft.MOD_ID, "starry_body"), "main");

    @Override
    public void onInitializeClient() {

        BlockRenders.defineRenders();
        ItemRenders.defineRenders();
        FluidRenders.defineRenders();
        BlockEntityRenders.defineRenders();


        EntityRendererRegistry.INSTANCE.register(EntityInit.STARRY_BODY, (context) -> {
            return new StarryBodyEntityRenderer(context);
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_STARRY_BODY_LAYER, StarryBodyEntityModel::getTexturedModelData);

//        ClientPlayNetworking.registerGlobalReceiver(AlkimiCraft.TIPI_FIRE_DATA_PACKET, (client, handler, buf, responseSender)->{
//
//        });

    }

}
