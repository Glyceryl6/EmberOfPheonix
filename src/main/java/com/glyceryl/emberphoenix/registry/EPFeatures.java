package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.world.features.BiggerLavaLakeFeature;
import com.glyceryl.emberphoenix.common.world.features.TripleLayerAshFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPFeatures {

    public static final Feature<BiggerLavaLakeFeature.Configuration> BIGGER_LAKE = EPBase.registerFeature("bigger_lake", new BiggerLavaLakeFeature(BiggerLavaLakeFeature.Configuration.CODEC));
    public static final Feature<NoneFeatureConfiguration> TRIPLE_ASH_LAYER = EPBase.registerFeature("triple_ash_layer", new TripleLayerAshFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        EPBase.FEATURES.register(eventBus);
    }

}