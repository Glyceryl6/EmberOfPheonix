package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.world.biomes.EPBiomesCreator;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPBiomes {

    public static final RegistryObject<Biome> ROSAANIA_BARREN;
    public static final RegistryObject<Biome> ROSAANIA_BARREN_HILLS;
    public static final RegistryObject<Biome> ROSAANIA_BARREN_FOREST;
    public static final RegistryObject<Biome> ROSAANIA_PLAIN;
    public static final RegistryObject<Biome> ROSAANIA_PLAIN_EDGE;
    public static final RegistryObject<Biome> ROSAANIA_VOLCANO;
    public static final RegistryObject<Biome> ROSAANIA_VOLCANO_PLAIN;
    public static final RegistryObject<Biome> TUMBLEWEED_PLAIN;
    public static final RegistryObject<Biome> ASH_PLAIN;

    public static void register(IEventBus eventBus) {
        RegistryBase.BIOMES.register(eventBus);
    }

    static {
        ROSAANIA_BARREN = RegistryBase.BIOMES.register("rosaania_barren", EPBiomesCreator::createRosaaniaBarren);
        ROSAANIA_BARREN_HILLS = RegistryBase.BIOMES.register("rosaania_barren_hills", EPBiomesCreator::createRosaaniaBarrenHills);
        ROSAANIA_BARREN_FOREST = RegistryBase.BIOMES.register("rosaania_barren_forest", EPBiomesCreator::createRosaaniaBarrenForest);
        ROSAANIA_PLAIN = RegistryBase.BIOMES.register("rosaania_plain", EPBiomesCreator::createRosaaniaPlains);
        ROSAANIA_PLAIN_EDGE = RegistryBase.BIOMES.register("rosaania_plain_edge", EPBiomesCreator::createRosaaniaPlainEdge);
        ROSAANIA_VOLCANO = RegistryBase.BIOMES.register("rosaania_volcano", EPBiomesCreator::createRosaaniaVolcano);
        ROSAANIA_VOLCANO_PLAIN = RegistryBase.BIOMES.register("rosaania_volcano_plain", EPBiomesCreator::createRosaaniaVolcanoPlains);
        TUMBLEWEED_PLAIN = RegistryBase.BIOMES.register("tumbleweed_plain", EPBiomesCreator::createTumbleweedPlains);
        ASH_PLAIN = RegistryBase.BIOMES.register("ash_plain", EPBiomesCreator::createAshPlain);
    }
}