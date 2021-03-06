package net.thenotfish.alkimicraft.client.renderer;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.AlkimiCraftClient;
import net.thenotfish.alkimicraft.client.model.StarryBodyEntityModel;
import net.thenotfish.alkimicraft.entity.StarryBodyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class StarryBodyEntityRenderer extends MobEntityRenderer<StarryBodyEntity, StarryBodyEntityModel> {

    public StarryBodyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new StarryBodyEntityModel(context.getPart(AlkimiCraftClient.MODEL_STARRY_BODY_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(StarryBodyEntity entity) {
        return new Identifier(AlkimiCraft.MOD_ID, "textures/entity/starry_body/starry_body.png");
    }

    protected int getBlockLight(StarryBodyEntity starryBodyEntity, BlockPos blockPos) {
        int i = (int) MathHelper.clampedLerp(0.0F, 15.0F, 1.0F);
        return i == 15 ? 15 : Math.max(i, super.getBlockLight(starryBodyEntity, blockPos));
    }
}
