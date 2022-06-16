package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.common.entity.boss.WildfireEntity;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public class FallingFireball extends ThrowableItemProjectile {

    public FallingFireball(EntityType<? extends FallingFireball> type, Level level) {
        super(type, level);
    }

    public FallingFireball(Level level, LivingEntity livingEntity) {
        super(EPEntity.FALLING_FIREBALL.get(), livingEntity, level);
    }

    public FallingFireball(double x, double y, double z, Level level) {
        super(EPEntity.FALLING_FIREBALL.get(), x, y, z, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            this.makeTrail2();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            if (!entity.fireImmune()) {
                Entity entity1 = getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(6);
                boolean flag = entity.hurt(DamageSource.ON_FIRE, 6.0F);
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    doEnchantDamageEffects((LivingEntity)entity1, entity);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level.isClientSide) {
            BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
            if (this.level.isEmptyBlock(blockpos)) {
                boolean canMakeFire = random.nextInt(100) % 3 == 0;
                boolean isPlayerOwner = this.getOwner() instanceof Player;
                boolean isWildfireOwner = this.getOwner() instanceof WildfireEntity;
                if (isPlayerOwner || (canMakeFire && isWildfireOwner)) {
                    this.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level, blockpos));
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level.isClientSide) {
            this.makeFlameParticles();
            this.discard();
        }
    }

    private void makeFlameParticles() {
        for(int i = 0; i < 5; ++i) {
            double dx = this.getRandomX(1.0D);
            double dy = this.getRandomY() + 1.0D;
            double dz = this.getRandomZ(1.0D);
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(ParticleTypes.LAVA, dx, dy, dz, d0, d1, d2);
        }
    }

    private void makeTrail() {
        for (int i = 0; i < 1; i++) {
            double sx = 0.5 * (random.nextDouble() - random.nextDouble()) + this.getDeltaMovement().x();
            double sy = 0.5 * (random.nextDouble() - random.nextDouble()) + this.getDeltaMovement().y();
            double sz = 0.5 * (random.nextDouble() - random.nextDouble()) + this.getDeltaMovement().z();
            double dx = getX() + sx;
            double dy = getY() + sy;
            double dz = getZ() + sz;
            level.addParticle(ParticleTypes.FLAME, dx, dy, dz, sx * -0.25, sy * -0.25, sz * -0.25);
        }
    }

    private void makeTrail2() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.level.addParticle(ParticleTypes.FLAME, d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

}