package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.common.entity.projectile.GatewayCreator;
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

public class GatewayCreatorRenderer extends EntityRenderer<GatewayCreator> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/item/diamond.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public GatewayCreatorRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected int getBlockLightLevel(GatewayCreator gatewayCreator, BlockPos blockPos) {
        return 15;
    }

    public void render(GatewayCreator gatewayCreator, float p_114081_, float p_114082_, PoseStack poseStack, MultiBufferSource source, int p_114085_) {
        poseStack.pushPose();
        poseStack.scale(1.5F, 1.5F, 1.5F);
        PoseStack.Pose pose = poseStack.last();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexconsumer = source.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(gatewayCreator, p_114081_, p_114082_, poseStack, source, p_114085_);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
        consumer.vertex(matrix4f, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_114096_,
                (float)p_114097_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114093_).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GatewayCreator p_114482_) {
        return TEXTURE_LOCATION;
    }

}