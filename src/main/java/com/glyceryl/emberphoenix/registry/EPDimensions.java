package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import org.slf4j.Logger;

@SuppressWarnings("unused")
public class EPDimensions {

    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final ResourceKey<Level> PHOENIX_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, EmberOfPhoenix.prefix("phoenix"));
    public static final ResourceKey<DimensionType> PHOENIX_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, PHOENIX_KEY.getRegistryName());

    public static void register() {
        LOGGER.info("Successfully registering dimensions for " + EmberOfPhoenix.MOD_ID);
    }
    
}