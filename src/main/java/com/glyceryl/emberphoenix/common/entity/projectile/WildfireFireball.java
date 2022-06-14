package com.glyceryl.emberphoenix.common.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WildfireFireball extends Fireball {

    public WildfireFireball(Level level, LivingEntity livingEntity, double x, double y, double z) {
        super(EntityType.SMALL_FIREBALL, livingEntity, x, y, z, level);
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
                boolean flag = entity.hurt(DamageSource.fireball(this, entity1), 6.0F);
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
                this.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level, blockpos));
            }
        }
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