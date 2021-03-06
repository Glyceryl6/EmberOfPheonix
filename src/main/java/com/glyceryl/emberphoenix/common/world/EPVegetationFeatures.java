package com.glyceryl.emberphoenix.common.world;

import com.glyceryl.emberphoenix.common.blocks.RedstoneBerryBush;
import com.glyceryl.emberphoenix.common.blocks.Tumbleweed;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class EPVegetationFeatures {

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FIRE_FLOWER = FeatureUtils.register("patch_fire_flower",
            Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EPBlocks.FIRE_FLOWER.get()), 5));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARREN_GRASS = FeatureUtils.register("patch_barren_grass",
            Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EPBlocks.BARREN_GRASS.get()), 32));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_TUMBLEWEED = FeatureUtils.register("patch_tumbleweed",
            Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(EPBlocks.TUMBLEWEED.get().defaultBlockState().setValue(Tumbleweed.FILLED, Boolean.FALSE)))));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARREN_TALL_GRASS = FeatureUtils.register("patch_barren_tall_grass",
            Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EPBlocks.BARREN_TALL_GRASS.get()))));

    public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> SINGLE_PIECE_OF_BARREN_GRASS = FeatureUtils.register("single_piece_of_barren_grass",
            Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EPBlocks.BARREN_GRASS.get().defaultBlockState())));

    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_REDSTONE_BERRY_BUSH = FeatureUtils.register("patch_redstone_berry_bush",
            Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(EPBlocks.REDSTONE_BERRY_BUSH.get().defaultBlockState().setValue(RedstoneBerryBush.AGE, 3)), 3));

    private static RandomPatchConfiguration grassPatch(BlockStateProvider provider, int i) {
        return FeatureUtils.simpleRandomPatchConfiguration(i, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(provider)));
    }

}