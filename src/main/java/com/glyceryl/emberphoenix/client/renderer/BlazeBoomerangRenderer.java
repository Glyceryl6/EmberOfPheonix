package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.common.entity.projectile.BoomerangFireball;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlazeBoomerangRenderer extends EntityRenderer<BoomerangFireball> {

    private static final ResourceLocation TEXTURE_LOCATION = EmberOfPhoenix.prefix("textures/entity/boomerang_fireball.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public BlazeBoomerangRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 2F;
        this.shadowStrength = 0.5F;
    }

    @Override
    public void render(BoomerangFireball entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(2.0f, 2.0f, 2.0f);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Vector3f.ZP.rotation((entityIn.age + partialTicks) * 0.9f));
        PoseStack.Pose poseStack$pose = poseStack.last();
        Matrix4f matrix4f = poseStack$pose.pose();
        Matrix3f matrix3f = poseStack$pose.normal();
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int lightmapUV, float x, int y, int u, int v) {
        consumer.vertex(matrix4f, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(BoomerangFireball entity) {
        return TEXTURE_LOCATION;
    }

}