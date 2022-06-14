package com.glyceryl.emberphoenix.client.renderer;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.common.entity.monster.AncientBlaze;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class AncientBlazeRenderer extends MobRenderer<AncientBlaze, BlazeModel<AncientBlaze>> {

    private static final ResourceLocation BLAZE_LOCATION = EmberOfPhoenix.prefix("textures/entity/ancient_blaze.png");

    public AncientBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeModel<>(context.bakeLayer(ModelLayers.BLAZE)), 0.5F);
    }

    protected int getBlockLightLevel(AncientBlaze p_113910_, BlockPos p_113911_) {
        return 15;
    }

    public ResourceLocation getTextureLocation(AncientBlaze p_113908_) {
        return BLAZE_LOCATION;
    }

}