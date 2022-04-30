package net.thenotfish.alkimicraft.client.renderer;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.AlkimiCraftClient;
import net.thenotfish.alkimicraft.client.model.CoppoiseEntityModel;
import net.thenotfish.alkimicraft.entity.CoppoiseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CoppoiseEntityRenderer extends MobEntityRenderer<CoppoiseEntity, CoppoiseEntityModel> {

    public CoppoiseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CoppoiseEntityModel(context.getPart(AlkimiCraftClient.MODEL_COPPOISE_LAYER)), 0.2f);
    }

    @Override
    public Identifier getTexture(CoppoiseEntity entity) {
        return new Identifier(AlkimiCraft.MOD_ID, "textures/entity/coppoise/coppoise.png");
    }


//    protected int getBlockLight(CoppoiseEntity coppoiseEntity, BlockPos blockPos) {
//        int i = (int) MathHelper.clampedLerp(0.0F, 15.0F, 1.0F);
//        return i == 15 ? 15 : Math.max(i, super.getBlockLight(coppoiseEntity, blockPos));
//    }
}
