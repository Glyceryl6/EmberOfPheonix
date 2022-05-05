package com.glyceryl.emberphoenix.common.world.registration;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class EPBiomesMaker {
    public static final Map<ResourceKey<Biome>, Biome> BIOMES = generateBiomes();

    private static Map<ResourceKey<Biome>, Biome> generateBiomes() {
        final ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes = ImmutableMap.builder();

        commonBiomes(biomes);

        return biomes.build();
    }
    private static void commonBiomes(ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
        biomes.put(EPBiomesKeys.ROSAANIA_BARREN, new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.MESA).temperature(2.0F).build());
    }

}