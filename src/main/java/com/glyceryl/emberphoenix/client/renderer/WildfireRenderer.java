package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.client.EPModelLayers;
import com.glyceryl.emberphoenix.client.model.WildfireModel;
import com.glyceryl.emberphoenix.common.entity.monster.WildfireEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WildfireRenderer extends MobRenderer<WildfireEntity, WildfireModel<WildfireEntity>> {

    private static final ResourceLocation BLAZE_LOCATION = EmberOfPhoenix.prefix("textures/entity/wildfire.png");

    public WildfireRenderer(EntityRendererProvider.Context context) {
        super(context, new WildfireModel<>(context.bakeLayer(EPModelLayers.LAYER_LOCATION)), 0.5F);
    }

    protected int getBlockLightLevel(WildfireEntity wildfireEntity, BlockPos blockPos) {
        return 15;
    }

    public ResourceLocation getTextureLocation(WildfireEntity entity) {
        return BLAZE_LOCATION;
    }

    @Override
    protected void scale(WildfireEntity entity, PoseStack stack, float partialTickTime) {
        float scale = 2.0F;
        stack.scale(scale, scale, scale);
    }

}