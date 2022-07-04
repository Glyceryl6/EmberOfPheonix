package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.common.entity.projectile.GatewayCreator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@SuppressWarnings("deprecation")
public class GatewayCreatorRenderer extends EntityRenderer<GatewayCreator> {

    public final ItemRenderer itemRenderer;

    public GatewayCreatorRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    protected int getBlockLightLevel(GatewayCreator gatewayCreator, BlockPos blockPos) {
        return 15;
    }

    public void render(GatewayCreator entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(1.5F, 1.5F, 1.5F);
        poseStack.mulPose(Vector3f.YP.rotation(entity.rotateSpeed));
        ItemStack stack = Items.DIAMOND.getDefaultInstance();
        BakedModel model = this.itemRenderer.getModel(stack, entity.level, null, 1);
        itemRenderer.render(stack, ItemTransforms.TransformType.NONE, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GatewayCreator entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}