package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHorizon {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlay(RenderBlockOverlayEvent event) {
        float f6 = EPEnchantHelper.getMagmaStrider(event.getPlayer());
        boolean hasEffect = event.getPlayer().hasEffect(MobEffects.FIRE_RESISTANCE);
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.FIRE) {
            return;
        }
        if (f6 > 0.0F || hasEffect) {
            if (event.getPlayer().isCreative()) {
                event.setCanceled(true);
            } else {
                event.getPoseStack().translate(0, -0.25, 0);
            }
        }
    }

}