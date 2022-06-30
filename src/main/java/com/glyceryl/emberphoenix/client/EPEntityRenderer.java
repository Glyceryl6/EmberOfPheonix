package com.glyceryl.emberphoenix.client;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.client.renderer.*;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EPEntityRenderer {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EPEntity.WILDFIRE.get(), WildfireRenderer::new);
        event.registerEntityRenderer(EPEntity.ANCIENT_BLAZE.get(), AncientBlazeRenderer::new);
        event.registerEntityRenderer(EPEntity.SMALL_CRACK.get(), SmallCrackRenderer::new);
        event.registerEntityRenderer(EPEntity.PALE_FIREBALL.get(), PaleFireballRenderer::new);
        event.registerEntityRenderer(EPEntity.FALLING_FIREBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EPEntity.BLAZE_BOOMERANG.get(), BlazeBoomerangRenderer::new);
        event.registerEntityRenderer(EPEntity.GATEWAY_CREATOR.get(), GatewayCreatorRenderer::new);
        event.registerEntityRenderer(EPEntity.PHOENIX_GATEWAY.get(), PhoenixGatewayRenderer::new);
    }

}