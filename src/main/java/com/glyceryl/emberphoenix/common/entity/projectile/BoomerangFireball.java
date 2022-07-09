package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Random;

public class BoomerangFireball extends AbstractHurtingProjectile {

    private static final DamageSource ARROW = (new DamageSource("arrow")).setProjectile();

    public int age;
    public float damage;
    public boolean returning;

    public BoomerangFireball(EntityType<? extends BoomerangFireball> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        returning = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (level.isClientSide || entity instanceof WildfireEntity) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity && this.returning) {
            livingEntity.hurt(ARROW, 8.0F); //给火球添加箭的伤害，预设伤害值为8点
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
            livingEntity.setSecondsOnFire(10);
            remove(RemovalReason.DISCARDED);
        }
        float pitch = 0.9f + entity.level.random.nextFloat() * 0.2f;
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, entity.getSoundSource(), 1.0F, pitch);
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        this.age++;
        if (level.isClientSide) {
            for (int i = 0; i < 5; i++) {
                Random random = new Random();
                float rotation = random.nextFloat();
                double x1 = Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX();
                double x2 = Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX();
                double z1 = Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ();
                double z2 = Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ();
                Vec3 vector = new Vec3(Math.cos(this.age) + this.getX(), getY(0.1), Math.sin(this.age) + this.getZ());
                level.addParticle(ParticleTypes.FLAME, x1, vector.y, z1, 0, 0, 0);
                level.addParticle(ParticleTypes.FLAME, x2, vector.y, z2, 0, 0, 0);
                level.addParticle(ParticleTypes.FLAME, vector.x, vector.y, vector.z, 0, 0, 0);
            }
        }

        if (this.isNoGravity()) {
            this.setSecondsOnFire(1);
        }

        if (!level.isClientSide) {
            if (getOwner() != null) {
                if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                    Vec3 vector3d = getDeltaMovement();
                    setYRot((float) (Mth.atan2(vector3d.y, vector3d.z) * (double) (180F / (float) Math.PI)));
                    yRotO = getYRot();
                    xRotO = getXRot();
                }
                float distance = distanceTo(getOwner());
                if (distance > 16.0D) {
                    returning = true;
                }
                if (returning) {
                    noPhysics = true;
                    Vec3 ownerPos = getOwner().position().add(0, 1, 0);
                    Vec3 motion = ownerPos.subtract(position());
                    setDeltaMovement(motion.normalize().scale(0.75f));
                }
                if (this.age > 8 && distance < 5.0F && isAlive()) {
                    remove(RemovalReason.DISCARDED);
                }
            } else {
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("returning", returning);
        compound.putFloat("damage", damage);
        compound.putInt("age", age);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        returning = compound.getBoolean("returning");
        damage = compound.getFloat("damage");
        age = compound.getInt("age");
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}