package com.glyceryl.emberphoenix.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class WildfireModel<T extends Entity> extends EntityModel<T> {

    private final ModelPart head;
    private final ModelPart shield4;
    private final ModelPart shield3;
    private final ModelPart shield2;
    private final ModelPart shield1;
    private final ModelPart body;

    public WildfireModel(ModelPart root) {
        this.head = root.getChild("head");
        this.shield4 = root.getChild("shield4");
        this.shield3 = root.getChild("shield3");
        this.shield2 = root.getChild("shield2");
        this.shield1 = root.getChild("shield1");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 0)
                .addBox(-4.0F, -26.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -27.0F, -4.5F, 9.0F, 9.0F, 9.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition shield4 = partdefinition.addOrReplaceChild("shield4", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        shield4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 18)
                        .addBox(12.0F, -14.0F, -5.0F, 2.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition shield3 = partdefinition.addOrReplaceChild("shield3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        shield3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 45)
                        .addBox(-5.0F, -14.0F, 12.0F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition shield2 = partdefinition.addOrReplaceChild("shield2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        shield2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 18)
                        .addBox(-14.0F, -14.0F, -5.0F, 2.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition shield1 = partdefinition.addOrReplaceChild("shield1", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        shield1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 45)
                        .addBox(-5.0F, -14.0F, -14.0F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(48, 16)
                        .addBox(-2.0F, -17.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.shield4.yRot = ageInTicks * 0.25F;
        this.shield3.yRot = ageInTicks * 0.25F;
        this.shield2.yRot = ageInTicks * 0.25F;
        this.shield1.yRot = ageInTicks * 0.25F - 540.5F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        shield4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        shield3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        shield2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        shield1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
