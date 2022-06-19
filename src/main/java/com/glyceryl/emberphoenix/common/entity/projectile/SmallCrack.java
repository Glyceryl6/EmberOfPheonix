package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import com.glyceryl.emberphoenix.common.entity.monster.AncientBlaze;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

public class SmallCrack extends AbstractHurtingProjectile {

    public int time;
    private int explosionPower = 2;
    private boolean makeFire = true;

    public SmallCrack(EntityType<? extends SmallCrack> type, Level level) {
        super(type, level);
    }

    public SmallCrack(Level level, LivingEntity entity, double x, double y, double z) {
        super(EPEntity.SMALL_CRACK.get(), entity, x, y, z, level);
    }

    //设置火球在生成时间超过10秒之后消失
    @Override
    public void tick() {
        super.tick();
        ++this.time;
        if (this.time >= 200) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (hitResult instanceof EntityHitResult) {
            if (((EntityHitResult)hitResult).getEntity() instanceof WildfireEntity
                    || ((EntityHitResult)hitResult).getEntity() instanceof SmallCrack
                    || ((EntityHitResult)hitResult).getEntity() instanceof AncientBlaze) {
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
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ExplosionPower", this.explosionPower);
        compoundTag.putBoolean("MakeFire", this.makeFire);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("ExplosionPower", 99)) {
            this.explosionPower = compoundTag.getInt("ExplosionPower");
        }
        if (compoundTag.contains("MakeFire", 99)) {
            this.makeFire = compoundTag.getBoolean("MakeFire");
        }
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

    public void setExplosionPower(int power) {
        this.explosionPower = power;
    }

    private void explode() {
        if (!this.level.isClientSide) {
            boolean b = random.nextInt(100) % 2 == 0 && this.makeFire;
            boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            Explosion.BlockInteraction type = flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, b, type);
            this.discard();
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}