package com.glyceryl.emberphoenix;

import com.glyceryl.emberphoenix.common.world.biomes.EPBiomesCreator;
import com.glyceryl.emberphoenix.event.LivingWalkOnMagma;
import com.glyceryl.emberphoenix.registry.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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
        EPSounds.register(eventBus);
        EPFeatures.register(eventBus);
        EPContainers.register(eventBus);
        EPEnchantments.register(eventBus);
        EPBiomesCreator.init();
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new LivingWalkOnMagma());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.ETERNAL_FIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.BARREN_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.BARREN_TALL_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.ETERNAL_FIRE_ALTAR.get(), RenderType.cutoutMipped());
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}
