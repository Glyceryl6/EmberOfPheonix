package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.world.biomes.EPBiomesCreator;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPBiomes {

    public static final RegistryObject<Biome> ROSAANIA_BARREN;
    public static final RegistryObject<Biome> ROSAANIA_PLAIN;
    public static final RegistryObject<Biome> ROSAANIA_VOLCANO;
    public static final RegistryObject<Biome> ASH_PLAIN;

    public static void register(IEventBus eventBus) {
        RegistryBase.BIOMES.register(eventBus);
    }

    static {
        ROSAANIA_BARREN = RegistryBase.BIOMES.register("rosaania_barren", () -> EPBiomesCreator.createDefaultBiomes(2.0F));
        ROSAANIA_PLAIN = RegistryBase.BIOMES.register("rosaania_plain", () -> EPBiomesCreator.createDefaultBiomes(2.2F));
        ROSAANIA_VOLCANO = RegistryBase.BIOMES.register("rosaania_volcano", EPBiomesCreator::createRosaaniaVolcano);
        ASH_PLAIN = RegistryBase.BIOMES.register("ash_plain", EPBiomesCreator::createAshPlain);
    }
}