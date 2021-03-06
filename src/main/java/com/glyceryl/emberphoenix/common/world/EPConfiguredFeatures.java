package com.glyceryl.emberphoenix.common.world;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.common.world.features.BiggerLavaLakeFeature;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.glyceryl.emberphoenix.registry.EPFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class EPConfiguredFeatures {

    public static final Holder<ConfiguredFeature<BiggerLavaLakeFeature.Configuration, ?>> BIGGER_LAKE_LAVA = register("bigger_lake_lava", EPFeatures.BIGGER_LAKE,
            new BiggerLavaLakeFeature.Configuration(BlockStateProvider.simple(Blocks.LAVA.defaultBlockState()), BlockStateProvider.simple(Blocks.LAVA)));

    public static final Holder<ConfiguredFeature<BiggerLavaLakeFeature.Configuration, ?>> SURFACE_HOLE = register("surface_hole", EPFeatures.BIGGER_LAKE,
            new BiggerLavaLakeFeature.Configuration(BlockStateProvider.simple(Blocks.AIR.defaultBlockState()), BlockStateProvider.simple(Blocks.AIR)));

    public static final Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> PETRIFIED_MUSHROOM = register("petrified_mushroom", EPFeatures.PETRIFIED_MUSHROOM,
            new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(EPBlocks.HARD_SLATE.get().defaultBlockState()), BlockStateProvider.simple(Blocks.BASALT.defaultBlockState()), 3));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, EmberOfPhoenix.prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
    }

}