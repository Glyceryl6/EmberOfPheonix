package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.world.biomes.RosaaniaBarren;
import com.glyceryl.emberphoenix.common.world.biomes.RosaaniaPlain;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class EPBiomes {

    public static final RegistryObject<Biome> ROSAANIA_BARREN;
    public static final RegistryObject<Biome> ROSAANIA_PLAIN;

    public EPBiomes() {}

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RosaaniaBarren.init();
            RosaaniaPlain.init();
        });
    }
    public static void register(IEventBus eventBus) {
        EPBase.BIOMES.register(eventBus);
    }

    static {
        ROSAANIA_BARREN = EPBase.BIOMES.register("rosaania_barren", RosaaniaBarren::createBiomes);
        ROSAANIA_PLAIN = EPBase.BIOMES.register("rosaania_plain", RosaaniaPlain::createBiomes);
    }

}