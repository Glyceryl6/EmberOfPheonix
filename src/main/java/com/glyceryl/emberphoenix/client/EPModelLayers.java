package com.glyceryl.emberphoenix.client;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EPModelLayers {

    public static final ModelLayerLocation LAYER_LOCATION = register("wildfire");

    private static ModelLayerLocation register(String s) {
        return register(s, "main");
    }

    private static ModelLayerLocation register(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(EmberOfPhoenix.MOD_ID, p_171301_), p_171302_);
    }

}