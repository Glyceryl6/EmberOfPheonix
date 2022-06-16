package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import com.glyceryl.emberphoenix.common.entity.monster.AncientBlaze;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ExplosionImmune {

    @SubscribeEvent
    public void setImmuneToExplode(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        boolean flag = entity instanceof WildfireEntity || entity instanceof AncientBlaze;
        if (event.getSource().isExplosion() && flag) {
            event.setCanceled(true);
        }
    }

}