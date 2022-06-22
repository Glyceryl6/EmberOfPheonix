package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEnvironment {

    Minecraft mc = Minecraft.getInstance();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderSun(RenderLevelLastEvent event) {
        Player player = mc.player;
        if (player != null) {
            Level level = player.level;
            ResourceKey<Level> resourceKey = level.dimension();
            if (resourceKey == EPDimensions.PHOENIX_KEY) {
                LevelRenderer.SUN_LOCATION = EmberOfPhoenix.prefix("textures/environment/sun.png");
            } else {
                LevelRenderer.SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
            }
        }
    }

}