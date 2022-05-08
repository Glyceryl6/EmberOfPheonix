package com.glyceryl.emberphoenix;

import com.glyceryl.emberphoenix.registry.EPBiomes;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.glyceryl.emberphoenix.registry.EPEntity;
import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

@Mod(EmberOfPhoenix.MOD_ID)
public class EmberOfPhoenix {

    public static final String MOD_ID = "emberphoenix";

    public EmberOfPhoenix() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EPItems.register(eventBus);
        EPBlocks.register(eventBus);
        EPBiomes.register(eventBus);
        EPEntity.register(eventBus);
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}
