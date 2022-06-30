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

    public void render(PhoenixGateway phoenixGateway, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float size = phoenixGateway.size;
        poseStack.scale(size, size, size);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        float f = Mth.rotLerp(partialTicks, phoenixGateway.yRotO, phoenixGateway.yRotO);
        setupRotations(phoenixGateway, poseStack, f);
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(phoenixGateway, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int lightmapUV, float x, int y, int u, int v) {
        consumer.vertex(matrix4f, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    //给传送门添加抖动的效果
    private void setupRotations(PhoenixGateway phoenixGateway, PoseStack poseStack, float rotationYaw) {
        rotationYaw += (float)(Math.cos((double)phoenixGateway.tickCount * 3.25D) * Math.PI * (double)0.4F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
    }

    @Override
    public ResourceLocation getTextureLocation(PhoenixGateway p_114482_) {
        return TEXTURE_LOCATION;
    }
}
