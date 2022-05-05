package com.glyceryl.emberphoenix.common.world.registration;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPBase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

public class EPBiomesKeys {

    public static final ResourceKey<Biome> ROSAANIA_BARREN = makeKey("rosaania_barren");
    public static final ResourceKey<Biome> ROSAANIA_BARREN_HILLS = makeKey("rosaania_barren_hills");
    public static final ResourceKey<Biome> ROSAANIA_PLAIN = makeKey("rosaania_plain");
    public static final ResourceKey<Biome> ROSAANIA_VOLCANO = makeKey("rosaania_volcano");
    public static final ResourceKey<Biome> ASH_PLAIN = makeKey("ash_plain");
    public static final ResourceKey<Biome> LAVA_RIVER = makeKey("lava_river");
    public static final ResourceKey<Biome> LAVA_OCEAN = makeKey("lava_ocean");

    public static final BiomeDictionary.Type EMBER_PHOENIX = BiomeDictionary.Type.getType("EMBER_PHOENIX");

    private static ResourceKey<Biome> makeKey(String name) {
        // Apparently this resolves biome shuffling /shrug
        EPBase.BIOMES.register(name, () -> new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.NONE)
                //.depth(0)
                .downfall(0)
                //.scale(0)
                .temperature(0)
                .specialEffects(new BiomeSpecialEffects.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build())
                .generationSettings(new BiomeGenerationSettings.Builder().build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build());
        return ResourceKey.create(Registry.BIOME_REGISTRY, EmberOfPhoenix.prefix(name));
    }

    public static void addBiomes() {
        BiomeDictionary.addTypes(ROSAANIA_BARREN, EMBER_PHOENIX, BiomeDictionary.Type.MESA);
        BiomeDictionary.addTypes(ROSAANIA_BARREN_HILLS, EMBER_PHOENIX, BiomeDictionary.Type.HILLS);
        BiomeDictionary.addTypes(ROSAANIA_PLAIN, EMBER_PHOENIX, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(ROSAANIA_VOLCANO, EMBER_PHOENIX, BiomeDictionary.Type.MOUNTAIN);
        BiomeDictionary.addTypes(ASH_PLAIN, EMBER_PHOENIX, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(LAVA_RIVER, EMBER_PHOENIX, BiomeDictionary.Type.RIVER);
        BiomeDictionary.addTypes(LAVA_OCEAN, EMBER_PHOENIX, BiomeDictionary.Type.OCEAN);
    }

}