package net.fabricmc.alkimicraft.client.renderer.block.entity;

import net.fabricmc.alkimicraft.blocks.entities.PackingTableBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Random;

//@Environment(EnvType.CLIENT)
public class PackingTableBlockEntityRenderer implements BlockEntityRenderer<PackingTableBlockEntity> {
    private static final float[][] pos = new float[16][4];
    private static final Random random = new Random();

    public PackingTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

    @Override
    public void render(PackingTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getItemsWaitingPacked().get(0);
        int itemNum = stack.getCount();

        // 移动物品
        for (int i = 0; i < Math.min(itemNum, 16); i++) {
            matrices.push();
            if (!entity.getPackFinished()) {
                matrices.translate(pos[i][0], 0.9, pos[i][2]);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(pos[i][1]));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                matrices.scale(pos[i][3], pos[i][3], pos[i][3]);
            } else {
                matrices.translate(0.5, 0.8, 0.5);
            }

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, (int) entity.getPos().asLong());
            matrices.pop();
        }

    }

    static {
        for (int i=0; i < pos.length; i++){
            pos[i][0] = random.nextFloat()*0.5f + 0.25f;
            pos[i][1] = random.nextFloat()*360f;
            pos[i][2] = random.nextFloat()*0.5f + 0.25f;
            pos[i][3] = random.nextFloat()*0.4f + 0.5f;
        }
    }

}
