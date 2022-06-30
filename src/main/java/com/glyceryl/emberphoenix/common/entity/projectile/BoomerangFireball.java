package com.glyceryl.emberphoenix.common.entity.projectile;

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

    public LivingEntity owner;

    public float damage;
    public int age;
    public int returnAge = 8;
    public boolean returning;

    public BoomerangFireball(EntityType<? extends BoomerangFireball> type, Level level) {
        super(type, level);
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float inaccuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        returning = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        DamageSource source = DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner());
        Entity entity = result.getEntity();
        if (level.isClientSide || entity.equals(owner)) {
            return;
        }
        if (entity.hurt(source, damage)) {
            if (!level.isClientSide && this.getOwner() != null) {
                if (entity instanceof LivingEntity livingEntity && !entity.is(getOwner())) {
                    this.level.explode(null, this.getX(), this.getY(), this.getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
                    livingEntity.setSecondsOnFire(10);
                    remove(RemovalReason.DISCARDED);
                }
            }
            returnAge += 4;
            float pitch = 0.9f + entity.level.random.nextFloat() * 0.2f;
            entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, entity.getSoundSource(), 1.0F, pitch);
        }
        super.onHitEntity(result);
    }

    @Override
    @SuppressWarnings("all")
    public void tick() {
        super.tick();
        this.age++;
        if (level.isClientSide) {
            for (int i = 0; i < 5; i++) {
                Random random = new Random();
                float rotation = random.nextFloat();
                Vec3 vector = new Vec3(Math.cos(this.age) * 0.8f + this.getX(), getY(0.1), Math.sin(this.age) * 0.8f + this.getZ());
                level.addParticle(ParticleTypes.FLAME, Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                level.addParticle(ParticleTypes.FLAME, Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
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
                    setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
                    yRotO = getYRot();
                    xRotO = getXRot();
                }
                if (this.age > returnAge) {
                    returning = true;
                }
                if (returning) {
                    noPhysics = true;
                    Vec3 ownerPos = getOwner().position().add(0, 1, 0);
                    Vec3 motion = ownerPos.subtract(position());
                    setDeltaMovement(motion.normalize().scale(0.75f));
                }
                float distance = distanceTo(getOwner());
                if (this.age > 16 && distance < 4.0F && isAlive()) {
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
        compound.putFloat("damage", damage);
        compound.putInt("age", age);
        compound.putBoolean("returning", returning);
        compound.putInt("returnAge", returnAge);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        damage = compound.getFloat("damage");
        age = compound.getInt("age");
        returning = compound.getBoolean("returning");
        returnAge = compound.getInt("returnAge");
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