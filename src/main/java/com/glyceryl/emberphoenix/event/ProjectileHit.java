package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ProjectileHit {

    @SubscribeEvent
    public void onPotionHit(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        AABB aabb = projectile.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> entityList = projectile.level.getEntitiesOfClass(LivingEntity.class, aabb);
        if (!entityList.isEmpty() && projectile instanceof ThrownPotion) {
            for(LivingEntity livingentity : entityList) {
                double d0 = projectile.distanceToSqr(livingentity);
                if (d0 < 16.0D && livingentity instanceof WildfireEntity && livingentity.isOnFire()) {
                    livingentity.hurt(DamageSource.indirectMagic(projectile, projectile.getOwner()), 20.0F);
                    livingentity.clearFire();
                }
            }
        }
    }

}