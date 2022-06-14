package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPEntity;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;
import java.util.Map;

public class SmallCrack extends AbstractHurtingProjectile {

    public SmallCrack(EntityType<? extends SmallCrack> type, Level level) {
        super(type, level);
    }

    public SmallCrack(Level level, LivingEntity entity, double x, double y, double z) {
        super(EPEntity.SMALL_CRACK.get(), entity, x, y, z, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level.isClientSide) {
            Entity entity = hitResult.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity && !entity1.fireImmune()) {
                this.createFireAndParticles(3);
                this.hurtParticularEntity(3.0F);
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.createFireAndParticles(3);
            this.hurtParticularEntity(3.0F);
        }
        this.discard();
    }

    //制造一个假的爆炸效果
    private void createFireAndParticles(int radius) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        levelRenderer.addParticle(ParticleTypes.EXPLOSION_EMITTER, false, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        levelRenderer.addParticle(ParticleTypes.EXPLOSION, false, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.explode(null, getX(), getY(), getZ(), 0.0F, false, Explosion.BlockInteraction.NONE);
        for(BlockPos blockPos : BlockPos.withinManhattan(this.blockPosition(), radius, radius, radius)) {
            boolean hasSolidBlock = this.level.getBlockState(blockPos.below()).isSolidRender(this.level, blockPos.below());
            if (this.random.nextInt(8) == 0 && this.level.getBlockState(blockPos).isAir() && hasSolidBlock) {
                this.level.setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level, blockPos));
            }
        }
    }

    //模仿原版的爆炸，并对特定的实体进行伤害
    private void hurtParticularEntity(float radius) {
        float f2 = radius * 2.0F;
        int k1 = Mth.floor(this.getX() - (double)f2 - 1.0D);
        int l1 = Mth.floor(this.getX() + (double)f2 + 1.0D);
        int i2 = Mth.floor(this.getY() - (double)f2 - 1.0D);
        int i1 = Mth.floor(this.getY() + (double)f2 + 1.0D);
        int j2 = Mth.floor(this.getZ() - (double)f2 - 1.0D);
        int j1 = Mth.floor(this.getZ() + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntitiesOfClass(Entity.class, new AABB(k1, i2, j2, l1, i1, j1));
        Vec3 vec3 = new Vec3(this.getX(), this.getY(), this.getZ());
        Map<Player, Vec3> hitPlayers = Maps.newHashMap();
        for (Entity entity : list) {
            double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
            if (d12 <= 1.0D) {
                double d5 = entity.getX() - this.getX();
                double d7 = entity.getY() - this.getY();
                double d9 = entity.getZ() - this.getZ();
                double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                if (d13 != 0.0D) {
                    d5 /= d13;
                    d7 /= d13;
                    d9 /= d13;
                    double d10 = (1.0D - d12);
                    double d11 = d10;
                    if (entity instanceof LivingEntity) {
                        d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
                    }
                    if (!(entity instanceof Monster)) {
                        entity.hurt(DamageSource.FALL, (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                        entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                    }
                    if (entity instanceof Player player) {
                        if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                            hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }
        }
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
    public boolean hurt(DamageSource source, float p_36911_) {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}