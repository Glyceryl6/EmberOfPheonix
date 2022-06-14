package com.glyceryl.emberphoenix.common.blocks;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

@SuppressWarnings("deprecation")
public class FireFlower extends TallFlowerBlock {

    private final Random random = new Random();

    public FireFlower(Properties properties) {
        super(properties);
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(EPBlocks.SCARLET_DIRT.get());
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof LivingEntity && !entity.fireImmune()) {
            spawnFlameParticles(entity);
            hurtAndBurningEntity(entity);
            knockBackEntity(entity);
            entity.level.levelEvent(1018, blockPos, 0);
        }
    }

    private void spawnFlameParticles(Entity entity) {
        if (entity.level.isClientSide) {
            LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
            for(int i = 0; i < 20; ++i) {
                double dx = entity.getRandomX(1.0D);
                double dy = entity.getRandomY();
                double dz = entity.getRandomZ(1.0D);
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                levelRenderer.addParticle(ParticleTypes.FLAME, false, dx, dy, dz, d0, d1, d2);
            }
        }
    }

    private void hurtAndBurningEntity(Entity entity) {
        entity.hurt(DamageSource.IN_FIRE, 7.0F);
        entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
        if (entity.getRemainingFireTicks() == 0) {
            entity.setSecondsOnFire(6);
        }
    }

    private void knockBackEntity(Entity entity) {
        int i = this.random.nextInt(3) + 1;
        float distanceOffset = switch (i) {
            case 1 -> 3.0F;
            case 2 -> 6.0F;
            default -> 9.0F;
        };
        double d1 = Mth.sin(entity.getYRot() * ((float)Math.PI / 180F));
        double d2 = -Mth.cos(entity.getYRot() * ((float)Math.PI / 180F));
        ((LivingEntity)entity).knockback(distanceOffset * 0.2F, d1, d2);
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(-0.6D, 1.0D, 0.6D));
    }

}