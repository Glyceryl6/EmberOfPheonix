package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.world.features.BiggerLavaLakeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPFeatures {

    public static final Feature<BiggerLavaLakeFeature.Configuration> BIGGER_LAKE = EPBase.registerFeature("bigger_lake", new BiggerLavaLakeFeature(BiggerLavaLakeFeature.Configuration.CODEC));

    public static void register(IEventBus eventBus) {
        EPBase.FEATURES.register(eventBus);
    }

}