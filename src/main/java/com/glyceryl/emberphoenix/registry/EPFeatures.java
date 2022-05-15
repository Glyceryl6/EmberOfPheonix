package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.world.features.BiggerLavaLakeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPFeatures {

    public static final Feature<BiggerLavaLakeFeature.Configuration> BIGGER_LAKE = RegistryBase.registerFeature("bigger_lake", new BiggerLavaLakeFeature(BiggerLavaLakeFeature.Configuration.CODEC));

    public static void register(IEventBus eventBus) {
        RegistryBase.FEATURES.register(eventBus);
    }

}