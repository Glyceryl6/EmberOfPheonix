package com.glyceryl.emberphoenix.common.world;

import com.glyceryl.emberphoenix.common.world.ore.EPOrePlacements;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class EPPlacedFeatures {

    //生成火流花
    public static final Holder<PlacedFeature> PATCH_FIRE_FLOWER = PlacementUtils.register("patch_fire_flower",
            EPVegetationFeatures.PATCH_FIRE_FLOWER, VegetationPlacements.worldSurfaceSquaredWithCount(5));

    //生成稀疏的火流花
    public static final Holder<PlacedFeature> PATCH_FIRE_FLOWER_SPARSE = PlacementUtils.register("patch_fire_flower_sparse",
            EPVegetationFeatures.PATCH_FIRE_FLOWER, VegetationPlacements.worldSurfaceSquaredWithCount(1));

    //生成大片的贫瘠草丛
    public static final Holder<PlacedFeature> PATCH_BARREN_GRASS = PlacementUtils.register("patch_barren_grass",
            EPVegetationFeatures.PATCH_BARREN_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(20));

    //生成稀疏的贫瘠草丛
    public static final Holder<PlacedFeature> PATCH_BARREN_GRASS_SPARSE = PlacementUtils.register("patch_barren_grass_sparse",
            EPVegetationFeatures.PATCH_BARREN_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(5));

    //生成风滚草
    public static final Holder<PlacedFeature> PATCH_TUMBLEWEED = PlacementUtils.register("patch_tumbleweed",
            EPVegetationFeatures.PATCH_TUMBLEWEED, VegetationPlacements.worldSurfaceSquaredWithCount(3));

    //生成贫瘠高草丛
    public static final Holder<PlacedFeature> PATCH_BARREN_TALL_GRASS = PlacementUtils.register("patch_barren_tall_grass",
            EPVegetationFeatures.PATCH_BARREN_TALL_GRASS, RarityFilter.onAverageOnceEvery(5),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //生成稀疏的贫瘠高草丛
    public static final Holder<PlacedFeature> PATCH_BARREN_TALL_GRASS_SPARSE = PlacementUtils.register("patch_barren_tall_grass_sparse",
            EPVegetationFeatures.PATCH_BARREN_TALL_GRASS, RarityFilter.onAverageOnceEvery(3),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //对猩红壤使用骨粉时生成的贫瘠草丛
    public static final Holder<PlacedFeature> SINGLE_PIECE_OF_BARREN_GRASS = PlacementUtils.register("barren_grass_bonemeal",
            EPVegetationFeatures.SINGLE_PIECE_OF_BARREN_GRASS, PlacementUtils.isEmpty());

    //生成比原版更大的岩浆湖
    public static final Holder<PlacedFeature> BIGGER_LAKE_LAVA_SURFACE = PlacementUtils.register("bigger_lake_lava_surface",
            EPConfiguredFeatures.BIGGER_LAKE_LAVA, RarityFilter.onAverageOnceEvery(50),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    //生成地表的坑洞
    public static final Holder<PlacedFeature> SURFACE_HOLE = PlacementUtils.register("surface_hole",
            EPConfiguredFeatures.SURFACE_HOLE, RarityFilter.onAverageOnceEvery(4),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    //生成石化巨型蘑菇森林
    public static final Holder<PlacedFeature> PETRIFIED_MUSHROOM = PlacementUtils.register("petrified_mushroom",
            EPConfiguredFeatures.PETRIFIED_MUSHROOM, VegetationPlacements.worldSurfaceSquaredWithCount(5));

    public static void addDefaultPhoenixUndergroundVariety(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_HARDSLATE);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_PHOENIX_SMOOTH_BASALT);
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