package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FallingFireball extends ThrowableItemProjectile {

    public int time;

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
        ++this.time;
        if (this.time >= 200) {
            this.discard();
        }
        if (this.isNoGravity()) {
            this.setSecondsOnFire(1);
        }
        if (this.level.isClientSide) {
            this.makeTrail(ParticleTypes.FLAME);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(6);
                Entity entity1 = getOwner();
                int i = entity.getRemainingFireTicks();
                float amount = this.isNoGravity() ? 3.0F : 6.0F;
                boolean flag = entity.hurt(DamageSource.ON_FIRE, amount);
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
            BlockState fireBlock = Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, 5);
            if (this.level.isEmptyBlock(blockpos) && !(this.getOwner() instanceof Player)) {
                this.level.setBlockAndUpdate(blockpos, fireBlock);
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level.isClientSide) {
            if (this.getOwner() instanceof Player) {
                this.makeFlameParticles();
            }
            this.discard();
        }
    }

    private void makeFlameParticles() {
        for(int i = 0; i < 50; ++i) {
            double dx = this.getRandomX(1.0D);
            double dy = this.getRandomY() + 1.0D;
            double dz = this.getRandomZ(1.0D);
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(ParticleTypes.LAVA, dx, dy, dz, d0, d1, d2);
        }
    }

    public void makeTrail(ParticleOptions particle) {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.level.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected Item getDefaultItem() {
        return this.isInvisible() ? Items.AIR : Items.FIRE_CHARGE;
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