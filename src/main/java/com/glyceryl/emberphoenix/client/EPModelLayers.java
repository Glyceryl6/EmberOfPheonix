package com.glyceryl.emberphoenix.client;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EPModelLayers {

    public static final ModelLayerLocation WILDFIRE_LAYER = register("wildfire");
    public static final ModelLayerLocation ANCIENT_BLAZE_LAYER = register("ancient_blaze");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    protected static ModelLayerLocation register(String path, String layer) {
        return new ModelLayerLocation(new ResourceLocation(EmberOfPhoenix.MOD_ID, path), layer);
    }

}