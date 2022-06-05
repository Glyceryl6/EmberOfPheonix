package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHorizon {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        float f6 = getMagmaStrider((LivingEntity) event.getCamera().getEntity());
        if (event.getCamera().getFluidInCamera() == FogType.WATER) {
            float waterVision = 1.0f;
            if(event.getCamera().getEntity() instanceof LocalPlayer) {
                waterVision = Math.max(0.25f, ((LocalPlayer)event.getCamera().getEntity()).getWaterVision());
            }
            event.scaleFarPlaneDistance(waterVision * 150.0F);
            event.setCanceled(true);
        } else if (event.getCamera().getFluidInCamera() == FogType.LAVA && f6 > 0.0F) {
            event.scaleFarPlaneDistance(30.0F * f6);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlay(RenderBlockOverlayEvent event) {
        float f6 = getMagmaStrider(event.getPlayer());
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.FIRE) {
            return;
        }
        if (f6 > 0.0F) {
            if (event.getPlayer().isCreative()) {
                event.setCanceled(true);
            } else if (event.getPlayer().isEyeInFluid(FluidTags.LAVA)) {
                event.getPoseStack().translate(0, -0.25, 0);
            }
        }
    }

    public static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}
