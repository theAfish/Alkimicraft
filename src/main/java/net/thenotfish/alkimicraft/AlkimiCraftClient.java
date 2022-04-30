package net.thenotfish.alkimicraft;

import net.thenotfish.alkimicraft.client.model.CoppoiseEntityModel;
import net.thenotfish.alkimicraft.client.model.StarryBodyEntityModel;
import net.thenotfish.alkimicraft.client.renderer.CoppoiseEntityRenderer;
import net.thenotfish.alkimicraft.client.renderer.StarryBodyEntityRenderer;
import net.thenotfish.alkimicraft.init.EntityInit;
import net.thenotfish.alkimicraft.init.ScreenInit;
import net.thenotfish.alkimicraft.screen.MicroscopeScreen;
import net.thenotfish.alkimicraft.screen.WoodenBarrelScreen;
import net.thenotfish.alkimicraft.utils.BlockEntityRenders;
import net.thenotfish.alkimicraft.utils.BlockRenders;
import net.thenotfish.alkimicraft.utils.FluidRenders;
import net.thenotfish.alkimicraft.utils.ItemRenders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AlkimiCraftClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_STARRY_BODY_LAYER = new EntityModelLayer(new Identifier(AlkimiCraft.MOD_ID, "starry_body"), "main");
    public static final EntityModelLayer MODEL_COPPOISE_LAYER = new EntityModelLayer(new Identifier(AlkimiCraft.MOD_ID, "coppoise"), "main");

    @Override
    public void onInitializeClient() {

        BlockRenders.defineRenders();
        ItemRenders.defineRenders();
        FluidRenders.defineRenders();
        BlockEntityRenders.defineRenders();
        ScreenRegistry.register(ScreenInit.WOODEN_BARREL_SCREEN_HANDLER, WoodenBarrelScreen::new);
        ScreenRegistry.register(ScreenInit.MICROSCOPE_SCREEN_HANDLER, MicroscopeScreen::new);



        EntityRendererRegistry.INSTANCE.register(EntityInit.STARRY_BODY, StarryBodyEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_STARRY_BODY_LAYER, StarryBodyEntityModel::getTexturedModelData);


        EntityRendererRegistry.INSTANCE.register(EntityInit.COPPOISE, CoppoiseEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_COPPOISE_LAYER, CoppoiseEntityModel::getTexturedModelData);

    }

}
