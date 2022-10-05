package com.glyceryl.emberphoenix;

import com.glyceryl.emberphoenix.common.world.biomes.EPBiomesCreator;
import com.glyceryl.emberphoenix.event.*;
import com.glyceryl.emberphoenix.registry.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

@Mod(EmberOfPhoenix.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EmberOfPhoenix {

    public static final String MOD_ID = "emberphoenix";
    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public EmberOfPhoenix() {
        EPBiomesCreator.init();
        EPDimensions.register();
        EPItems.register(eventBus);
        EPBlocks.register(eventBus);
        EPBiomes.register(eventBus);
        EPEntity.register(eventBus);
        EPSounds.register(eventBus);
        EPFeatures.register(eventBus);
        EPParticles.register(eventBus);
        EPContainers.register(eventBus);
        EPEnchantments.register(eventBus);
        eventBus.addListener(this::registerBow);
        eventBus.addListener(this::registerRenderLayer);
        eventBus.addGenericListener(SoundEvent.class, EPSounds::registerSounds);
        MinecraftForge.EVENT_BUS.register(new ProjectileExplosion());
        MinecraftForge.EVENT_BUS.register(new RenderEnvironment());
        MinecraftForge.EVENT_BUS.register(new LivingWalkOnMagma());
        MinecraftForge.EVENT_BUS.register(new PlayerUseTrident());
        MinecraftForge.EVENT_BUS.register(new ExplosionImmune());
        MinecraftForge.EVENT_BUS.register(new ThrowFireCharge());
        MinecraftForge.EVENT_BUS.register(new ProjectileHit());
        MinecraftForge.EVENT_BUS.register(new RenderHorizon());
        MinecraftForge.EVENT_BUS.register(new ActivateAltar());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.TUMBLEWEED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.FIRE_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.ETERNAL_FIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.BARREN_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.WILDFIRE_FIGURE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.TUMBLEWEED_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.BARREN_TALL_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.TUMBLEWEED_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.REDSTONE_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(EPBlocks.ETERNAL_FIRE_ALTAR.get(), RenderType.cutoutMipped());
    }

    private void registerBow(FMLClientSetupEvent event) {
        ItemProperties.register(EPItems.BLAZE_BOW.get(), new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(EPItems.BLAZE_BOW.get(), new ResourceLocation("pulling"), (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}