package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import net.minecraftforge.eventbus.api.IEventBus;

public class EPEntity {

    public static void register(IEventBus eventBus) {
        RegistryBase.ENTITY.register(eventBus);
    }

}