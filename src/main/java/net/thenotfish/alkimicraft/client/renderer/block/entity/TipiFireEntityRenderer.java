package net.thenotfish.alkimicraft.client.renderer.block.entity;

import net.thenotfish.alkimicraft.blocks.TipiFire;
import net.thenotfish.alkimicraft.blocks.entities.TipiFireEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class TipiFireEntityRenderer implements BlockEntityRenderer<TipiFireEntity> {
    private static final float SCALE = 0.375F;

    public TipiFireEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public void render(TipiFireEntity tipiFireEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        Direction direction = tipiFireEntity.getCachedState().get(TipiFire.FACING);
        DefaultedList<ItemStack> defaultedList = tipiFireEntity.getItemsBeingCooked();

        int k = (int)tipiFireEntity.getPos().asLong();

        for(int l = 0; l < defaultedList.size(); ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack != ItemStack.EMPTY) {
                if (l < 4) {
                    matrixStack.push();
                    matrixStack.translate(0.5D, 0.15D, 0.5D);
                    Direction direction2 = Direction.fromHorizontal((l + direction.getHorizontal()) % 4);
                    float g = -direction2.asRotation();
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
                    matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                    matrixStack.translate(-0.2D, -0.2D, 0.0D);
                    matrixStack.scale(0.375F, 0.375F, 0.375F);
                    MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrixStack, vertexConsumerProvider, (int) tipiFireEntity.getPos().asLong());
                    matrixStack.pop();
                }else {
                    matrixStack.push();
                    matrixStack.translate(0.5D, 0.15D, 0.5D);
                    Direction direction2 = Direction.fromHorizontal((l + direction.getHorizontal()) % 4);
                    float g = -direction2.asRotation();
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(g));
                    matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
                    matrixStack.translate(-0.15D, 0.0D, 0.0D);
                    matrixStack.scale(0.25F, 0.25F, 0.25F);
                    MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrixStack, vertexConsumerProvider, (int) tipiFireEntity.getPos().asLong());
                    matrixStack.pop();
                }
            }
        }
    }
}