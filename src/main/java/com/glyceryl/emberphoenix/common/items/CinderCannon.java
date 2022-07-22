package com.glyceryl.emberphoenix.common.items;

import com.glyceryl.emberphoenix.registry.EPSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CinderCannon extends Item {

    public CinderCannon(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(this)) return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));
        player.playSound(EPSounds.CANNON_SHOOTING, 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F);
        if (!level.isClientSide) {
            this.doBounce(level, player);
        } else {
            AABB aabb = getEffectAABB(player);
            Vec3 lookVec = player.getLookAngle();
            for (int i = 0; i < 50; i++) {
                double x = aabb.minX + level.random.nextFloat() * (aabb.maxX - aabb.minX);
                double y = aabb.minY + level.random.nextFloat() * (aabb.maxY - aabb.minY);
                double z = aabb.minZ + level.random.nextFloat() * (aabb.maxZ - aabb.minZ);
                level.addParticle(ParticleTypes.CLOUD, x, y, z, lookVec.x, lookVec.y, lookVec.z);
            }
        }
        player.startUsingItem(hand);
        player.getCooldowns().addCooldown(this, 40);
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    private void doBounce(Level world, Player player) {
        AABB aabb = getEffectAABB(player);
        this.bounceEntitiesInAABB(world, player, aabb);
    }

    private void bounceEntitiesInAABB(Level level, Player player, AABB aabb) {
        double factor = player.isSprinting() ? 4.0D : 3.0D;
        Vec3 moveVec = player.getLookAngle().scale(factor);
        for (Entity entity : level.getEntitiesOfClass(Entity.class, aabb)) {
            if (entity.isPushable() || entity instanceof ItemEntity || entity instanceof Projectile) {
                entity.setDeltaMovement(moveVec.x, 0.5D, moveVec.z);
            }
        }
    }

    private AABB getEffectAABB(Player player) {
        double range = 4.0D;
        double radius = 3.0D;
        Vec3 srcVec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 lookVec = player.getLookAngle().scale(range);
        Vec3 destVec = srcVec.add(lookVec.x, lookVec.y, lookVec.z);
        return new AABB(destVec.x - radius, destVec.y - radius, destVec.z - radius, destVec.x + radius, destVec.y + radius, destVec.z + radius);
    }


}