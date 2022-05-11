package com.glyceryl.emberphoenix.common.world.biomes;

import com.glyceryl.emberphoenix.common.world.EPPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class EPBiomesCreator {

    public static Biome createRosaaniaBarren() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder())
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NETHER).temperature(2.0F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                        .skyColor(OverworldBiomes.calculateSkyColor(2.0F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createRosaaniaPlain() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder())
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NETHER).temperature(2.2F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                        .skyColor(OverworldBiomes.calculateSkyColor(2.0F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static Biome createAshPlain() {
        MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).build();
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder())
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EPPlacedFeatures.BIGGER_LAKE_LAVA_SURFACE);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        EPPlacedFeatures.addDefaultPhoenixOres(builder);
        EPPlacedFeatures.addDefaultPhoenixUndergroundVariety(builder);
        return (new Biome.BiomeBuilder()).precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NETHER).temperature(1.8F).downfall(0.0F)
                .specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(3344392)
                        .skyColor(OverworldBiomes.calculateSkyColor(2.0F)).build()).mobSpawnSettings(mobspawnsettings).generationSettings(builder.build()).build();
    }

    public static void init() {}

}