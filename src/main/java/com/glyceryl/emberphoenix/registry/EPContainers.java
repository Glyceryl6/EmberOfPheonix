package com.glyceryl.emberphoenix.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class EPContainers {

    public static void register(IEventBus eventBus) {
        EPBase.CONTAINER.register(eventBus);
    }

}
