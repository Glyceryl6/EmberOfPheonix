package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHorizon {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlay(RenderBlockOverlayEvent event) {
        float f6 = EPEnchantHelper.getMagmaStrider(event.getPlayer());
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

}