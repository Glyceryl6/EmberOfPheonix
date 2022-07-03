package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.client.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EPParticles {

    public static final RegistryObject<SimpleParticleType> WILDFIRE_HEAL = simple("wildfire_heal");
    public static final RegistryObject<SimpleParticleType> LARGE_FLAME = simple("large_flame");

    private static RegistryObject<SimpleParticleType> simple(String name) {
        return RegistryBase.PARTICLES.register(name, () -> new SimpleParticleType(false));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent event) {
        ParticleEngine particles = Minecraft.getInstance().particleEngine;
        particles.register(EPParticles.WILDFIRE_HEAL.get(), WildfireHealParticle.Factory::new);
        particles.register(EPParticles.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.PARTICLES.register(eventBus);
    }

}