package com.glyceryl.emberphoenix.client;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.client.model.WildfireModel;
import net.minecraft.client.model.BlazeModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EPLayerDefinitions {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EPModelLayers.WILDFIRE_LAYER, WildfireModel::createBodyLayer);
        event.registerLayerDefinition(EPModelLayers.ANCIENT_BLAZE_LAYER, BlazeModel::createBodyLayer);
    }

}