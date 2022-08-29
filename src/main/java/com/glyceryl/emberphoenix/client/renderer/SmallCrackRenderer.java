package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.common.entity.projectile.SmallCrack;
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

public class SmallCrackRenderer extends EntityRenderer<SmallCrack> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/particle/angry.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public SmallCrackRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    protected int getBlockLightLevel(SmallCrack smallCrack, BlockPos blockPos) {
        return 15;
    }

    public void render(SmallCrack smallCrack, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        PoseStack.Pose poseStack$pose = poseStack.last();
        Matrix4f matrix4f = poseStack$pose.pose();
        Matrix3f matrix3f = poseStack$pose.normal();
        VertexConsumer vertexconsumer = source.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(smallCrack, entityYaw, partialTicks, poseStack, source, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int lightmapUV, float x, int y, int u, int v) {
        consumer.vertex(matrix4f, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public ResourceLocation getTextureLocation(SmallCrack smallCrack) {
        return TEXTURE_LOCATION;
    }
    
}
