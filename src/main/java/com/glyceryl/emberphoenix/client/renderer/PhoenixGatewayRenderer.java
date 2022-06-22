package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.common.entity.projectile.PhoenixGateway;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PhoenixGatewayRenderer extends EntityRenderer<PhoenixGateway> {

    private static final ResourceLocation TEXTURE_LOCATION = EmberOfPhoenix.prefix("textures/entity/phoenix_gateway.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public PhoenixGatewayRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected int getBlockLightLevel(PhoenixGateway phoenixGateway, BlockPos blockPos) {
        return 15;
    }

    public void render(PhoenixGateway phoenixGateway, float p_114081_, float p_114082_, PoseStack poseStack, MultiBufferSource source, int p_114085_) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        PoseStack.Pose pose = poseStack.last();
        float f = Mth.rotLerp(p_114082_, phoenixGateway.yRotO, phoenixGateway.yRotO);
        this.setupRotations(phoenixGateway, poseStack, f);
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexconsumer = source.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(phoenixGateway, p_114081_, p_114082_, poseStack, source, p_114085_);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
        consumer.vertex(matrix4f, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_114096_,
                (float)p_114097_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114093_).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private void setupRotations(PhoenixGateway phoenixGateway, PoseStack poseStack, float p_115320_) {
        p_115320_ += (float)(Math.cos((double)phoenixGateway.tickCount * 3.25D) * Math.PI * (double)0.4F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115320_));
    }

    @Override
    public ResourceLocation getTextureLocation(PhoenixGateway p_114482_) {
        return TEXTURE_LOCATION;
    }
}
