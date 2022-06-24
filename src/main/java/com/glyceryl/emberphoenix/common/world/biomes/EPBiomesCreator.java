package com.glyceryl.emberphoenix.common.world.biomes;

import com.glyceryl.emberphoenix.common.world.EPPlacedFeatures;
import com.glyceryl.emberphoenix.common.world.ore.EPOrePlacements;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class EPBiomesCreator {

    public static Biome createRosaaniaPlains() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_FIRE_FLOWER)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_BARREN_GRASS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_BARREN_TALL_GRASS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_REDSTONE_BERRY_COMMON)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(2.2F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaPlainEdge() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_FIRE_FLOWER_SPARSE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_REDSTONE_BERRY_RARE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_BARREN_GRASS_SPARSE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_BARREN_TALL_GRASS_SPARSE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(2.2F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createTumbleweedPlains() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PATCH_TUMBLEWEED)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.PLAINS).temperature(2.2F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaBarren() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.EXTREME_HILLS).temperature(2.0F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaBarrenForest() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.PETRIFIED_MUSHROOM);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.EXTREME_HILLS).temperature(2.0F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaBarrenHills() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.EXTREME_HILLS).temperature(2.0F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.1F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaVolcano() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.SURFACE_HOLE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.MESA).temperature(2.8F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.3F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaVolcanoPlains() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.SURFACE_HOLE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.MESA).temperature(2.8F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.3F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createAshPlain() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder());
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EPOrePlacements.ORE_ASH_BLOCK)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.DESERT).temperature(1.8F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                .skyColor(OverworldBiomes.calculateSkyColor(0.2F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static void init() {}

}