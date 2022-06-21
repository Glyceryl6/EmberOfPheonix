package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.world.features.BiggerLavaLakeFeature;
import com.glyceryl.emberphoenix.common.world.features.PetrifiedMushroom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPFeatures {

    public static final Feature<BiggerLavaLakeFeature.Configuration> BIGGER_LAKE = RegistryBase.registerFeature("bigger_lake", new BiggerLavaLakeFeature(BiggerLavaLakeFeature.Configuration.CODEC));
    public static final Feature<HugeMushroomFeatureConfiguration> PETRIFIED_MUSHROOM = RegistryBase.registerFeature("petrified_mushroom", new PetrifiedMushroom(HugeMushroomFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        RegistryBase.FEATURES.register(eventBus);
    }

}