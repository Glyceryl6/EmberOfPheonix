package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import com.glyceryl.emberphoenix.common.entity.monster.AncientBlaze;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class SmallCrack extends AbstractHurtingProjectile {

    public int time;

    public SmallCrack(EntityType<? extends SmallCrack> type, Level level) {
        super(type, level);
    }

    public SmallCrack(Level level, LivingEntity entity, double x, double y, double z) {
        super(EPEntity.SMALL_CRACK.get(), entity, x, y, z, level);
    }

    @Override
    public void tick() {
        super.tick();
        ++this.time;
        if (this.time >= 200) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level.isClientSide) {
            Entity entity = hitResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity && !entity1.fireImmune()) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if (result instanceof EntityHitResult) {
            if (((EntityHitResult)result).getEntity() instanceof WildfireEntity
                    || ((EntityHitResult)result).getEntity() instanceof SmallCrack
                    || ((EntityHitResult)result).getEntity() instanceof AncientBlaze) {
                return;
            }
        }
        explode();
    }

    //设置它飞出时尾部的粒子轨迹
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.LARGE_SMOKE;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        super.hurt(source, amount);
        if (source.getDirectEntity() != null) {
            if (!source.isExplosion())
                explode();
            return true;
        } else {
            return false;
        }
    }

    private void explode() {
        if (!this.level.isClientSide) {
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), 3.0F, true, Explosion.BlockInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}