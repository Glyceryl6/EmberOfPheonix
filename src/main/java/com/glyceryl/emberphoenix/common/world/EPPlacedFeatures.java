package com.glyceryl.emberphoenix.common.world;

import com.glyceryl.emberphoenix.common.world.ore.EPOrePlacements;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.*;

public class EPPlacedFeatures {

    public static final Holder<PlacedFeature> PATCH_BARREN_GRASS = PlacementUtils.register("patch_barren_grass",
            EPVegetationFeatures.PATCH_BARREN_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(20));

    public static final Holder<PlacedFeature> PATCH_BARREN_TALL_GRASS = PlacementUtils.register("patch_barren_tall_grass",
            EPVegetationFeatures.PATCH_BARREN_TALL_GRASS, RarityFilter.onAverageOnceEvery(5),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> BIGGER_LAKE_LAVA_SURFACE = PlacementUtils.register("bigger_lake_lava_surface",
            EPConfiguredFeatures.BIGGER_LAKE_LAVA, RarityFilter.onAverageOnceEvery(100),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static void addDefaultPhoenixUndergroundVariety(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_HARDSLATE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_SMOOTH_BASALT);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_AMETHYST);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_DENSE_GLOWSTONE);
    }

    public static void addDefaultPhoenixOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_COAL);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_IRON);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_COPPER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_SILVER);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_GOLD);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_DIAMOND);
    }

}