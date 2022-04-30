package net.thenotfish.alkimicraft.mixin;

import net.thenotfish.alkimicraft.init.ItemInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Shadow
    private ItemStack offHand;

    @Shadow
    private ItemStack mainHand;

    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "renderFirstPersonItem",at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;push()V",
            shift = At.Shift.AFTER), cancellable = true)
    private void renderCoppoiseInHand(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, Hand hand, float h, ItemStack itemStack, float i, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int j,CallbackInfo ci){
        boolean bl = hand == Hand.MAIN_HAND;
        if (itemStack.isOf(ItemInit.COPPOISE_IN_BAG)) {
            if (bl && this.offHand.isEmpty()) {
                this.renderInBothHands(abstractClientPlayerEntity, itemStack,matrixStack, vertexConsumerProvider, j, g, i, h);
                matrixStack.pop();
                ci.cancel();
            }
        }
    }

    private void renderInBothHands(AbstractClientPlayerEntity abstractClientPlayerEntity, ItemStack itemStack, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, float f, float g, float h) {
        float j = MathHelper.sqrt(h);
        float k = -0.2F * MathHelper.sin(h * 3.1415927F);
        float l = -0.4F * MathHelper.sin(j * 3.1415927F);
        matrixStack.translate(0.0D, (-k / 2.0F), l);
        float m = this.getAngle(f);
        matrixStack.translate(0.0D, (0.04F + g * -1.2F + m * -0.5F), -0.7200000286102295D);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(m * -85.0F));
        assert this.client.player != null;
        if (!this.client.player.isInvisible()) {
            matrixStack.push();
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
            this.renderArm(matrixStack, vertexConsumerProvider, i, Arm.RIGHT);
            this.renderArm(matrixStack, vertexConsumerProvider, i, Arm.LEFT);
            matrixStack.pop();
        }

        float n = MathHelper.sin(j * 3.1415927F);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(n * 20.0F));
        matrixStack.scale(2.0F, 2.0F, 2.0F);
        this.renderItem(abstractClientPlayerEntity, itemStack, ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
    }

    private float getAngle(float f) {
        float g = 1.0F - f / 45.0F + 0.1F;
        g = MathHelper.clamp(g, 0.0F, 1.0F);
        g = -MathHelper.cos(g * 3.1415927F) * 0.5F + 0.5F;
        return g;
    }

    @Shadow
    private void renderArm(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, Arm arm){}

    @Shadow
    public void renderItem(LivingEntity livingEntity, ItemStack itemStack, ModelTransformation.Mode mode, boolean bl, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {}
}
