package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class PaleFireball extends AbstractHurtingProjectile {

    public int time;

    public PaleFireball(EntityType<? extends PaleFireball> type, Level level) {
        super(type, level);
    }

    public PaleFireball(Level level, LivingEntity entity, double x, double y, double z) {
        super(EPEntity.PALE_FIREBALL.get(), entity, x, y, z, level);
    }

    private void knockBackEntity(Entity entity) {
        int i = this.random.nextInt(3) + 1;
        float distanceOffset = switch (i) {
            case 1 -> 3.0F;
            case 2 -> 5.0F;
            default -> 7.0F;
        };
        double d1 = Mth.sin(entity.getYRot() * ((float)Math.PI / 180F));
        double d2 = -Mth.cos(entity.getYRot() * ((float)Math.PI / 180F));
        ((LivingEntity)entity).knockback(distanceOffset * 0.2F, d1, d2);
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(-0.6D, 1.0D, 0.6D));
    }

    private void makePoofParticles() {
        for(int i = 0; i < 50; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double x = this.getRandomX(4.0D);
            double y = this.getRandomY();
            double z = this.getRandomZ(4.0D);
            this.level.addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
        }
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
            if (!entity.fireImmune()) {
                Entity entity1 = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(5);
                this.knockBackEntity(entity);
                this.makePoofParticles();
                boolean flag = entity.hurt(DamageSource.MAGIC, 9.0F);
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.doEnchantDamageEffects((LivingEntity)entity1, entity);
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level.isClientSide) {
            this.makePoofParticles();
            this.discard();
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.POOF;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}