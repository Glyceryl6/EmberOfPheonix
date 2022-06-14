package com.glyceryl.emberphoenix.client;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EPModelLayers {

    public static final ModelLayerLocation WILDFIRE_LAYER = register("wildfire");
    public static final ModelLayerLocation ANCIENT_BLAZE_LAYER = register("ancient_blaze");

    private static ModelLayerLocation register(String s) {
        return register(s, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(EmberOfPhoenix.MOD_ID, p_171301_), p_171302_);
    }

}