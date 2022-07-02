package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.common.entity.projectile.BoomerangFireball;
import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

@SuppressWarnings("deprecation")
public class BlazeBoomerangRenderer extends EntityRenderer<BoomerangFireball> {

    public final ItemRenderer itemRenderer;

    public BlazeBoomerangRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 2F;
        this.shadowStrength = 0.5F;
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BoomerangFireball entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        ItemStack itemStack = EPItems.BOOMERANG_FIREBALL.get().getDefaultInstance();
        BakedModel model = this.itemRenderer.getModel(itemStack, entity.level, null, 1);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180F));
        poseStack.mulPose(Vector3f.ZP.rotation((entity.age + partialTicks) * 0.7f));
        itemRenderer.render(itemStack, ItemTransforms.TransformType.NONE, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BoomerangFireball entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}