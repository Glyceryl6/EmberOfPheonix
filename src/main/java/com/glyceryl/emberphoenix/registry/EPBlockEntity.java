package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPBlockEntity {

    public static void register(IEventBus eventBus) {
        RegistryBase.BLOCK_ENTITY.register(eventBus);
    }

}