package com.glyceryl.emberphoenix.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class EPEntity {

    public static void register(IEventBus eventBus) {
        EPBase.ENTITY.register(eventBus);
    }

}
