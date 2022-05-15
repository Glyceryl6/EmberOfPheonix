package com.glyceryl.emberphoenix.common.world;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class EPVegetationFeatures {

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARREN_GRASS = FeatureUtils.register("patch_barren_grass",
            Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EPBlocks.BARREN_GRASS.get()), 32));
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARREN_TALL_GRASS = FeatureUtils.register("patch_barren_tall_grass",
            Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EPBlocks.BARREN_TALL_GRASS.get()))));

    @SuppressWarnings("all")
    private static RandomPatchConfiguration grassPatch(BlockStateProvider provider, int i) {
        return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(provider)));
    }

}
