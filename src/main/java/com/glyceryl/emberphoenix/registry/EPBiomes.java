package com.glyceryl.emberphoenix.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class EPBiomes {

    public static void register(IEventBus eventBus) {
        EPBase.BIOMES.register(eventBus);
    }

}
