package net.fabricmc.alkimicraft.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.alkimicraft.entity.CoppoiseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class CoppoiseEntityModel extends AnimalModel<CoppoiseEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public CoppoiseEntityModel(ModelPart root) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        ModelPartData modelPartBody = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, -1.57F, 0.0F));
        Dilation dilation = new Dilation(0.001F);
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 2.0F, 1.0F, 2.0F, 2.0F, 2.0F, dilation);
        ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(0, 4).cuboid(1.0F, 2.0F, 1.0F, 2.0F, 2.0F, 2.0F, dilation);
        modelPartData.addChild("left_hind_leg", modelPartBuilder2, ModelTransform.pivot(-4.0F, 20.0F, -4.0F));
        modelPartData.addChild("left_front_leg", modelPartBuilder, ModelTransform.pivot(0.0F, 20.0F, -4.0F));
        modelPartData.addChild("right_hind_leg", modelPartBuilder2, ModelTransform.pivot(-4.0F, 20.0F, 0.0F));
        modelPartData.addChild("right_front_leg", modelPartBuilder, ModelTransform.pivot(0.0F, 20.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 14);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
    }

    @Override
    public void setAngles(CoppoiseEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float r = 2.4662f;
        this.rightHindLeg.pitch = MathHelper.cos(limbAngle * r) * 1.0F * limbDistance;
        this.leftHindLeg.pitch = MathHelper.cos(limbAngle * r + 3.1415927F) * 1.0F * limbDistance;
        this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * r + 3.1415927F) * 1.0F * limbDistance;
        this.leftFrontLeg.pitch = MathHelper.cos( limbAngle * r) * 1.0F * limbDistance;
    }
}
