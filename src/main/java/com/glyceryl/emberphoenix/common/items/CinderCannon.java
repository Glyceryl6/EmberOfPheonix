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
        if(player.getCooldowns().isOnCooldown(this)) return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));
        if (!level.isClientSide) {
            int shoot = doBounce(level, player);
            player.getItemInHand(hand).hurtAndBreak(shoot + 1, player, (user) -> user.broadcastBreakEvent(hand));
        } else {
            AABB aabb = getEffectAABB(player);
            Vec3 lookVec = player.getLookAngle();
            for (int i = 0; i < 50; i++) {
                level.addParticle(ParticleTypes.CLOUD, aabb.minX + level.random.nextFloat() * (aabb.maxX - aabb.minX),
                        aabb.minY + level.random.nextFloat() * (aabb.maxY - aabb.minY),
                        aabb.minZ + level.random.nextFloat() * (aabb.maxZ - aabb.minZ),
                        lookVec.x, lookVec.y, lookVec.z);
            }

            player.playSound(EPSounds.CANNON_SHOOTING, 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F);
        }
        player.startUsingItem(hand);
        player.getCooldowns().addCooldown(this, 40);
        return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if(entity instanceof Player player && player.isFallFlying() && (player.getItemInHand(InteractionHand.OFF_HAND).is(this) || isSelected)) {
            player.fallDistance = 0.0F;
        }
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
    }

    private int doBounce(Level world, Player player) {
        AABB aabb = getEffectAABB(player);
        return bounceEntitiesInAABB(world, player, aabb);
    }

    private int bounceEntitiesInAABB(Level level, Player player, AABB aabb) {
        double factor = player.isSprinting() ? 4.0D : 3.0D;
        Vec3 moveVec = player.getLookAngle().scale(factor);
        int bouncedEntities = 0;
        for (Entity entity : level.getEntitiesOfClass(Entity.class, aabb)) {
            if (entity.isPushable() || entity instanceof ItemEntity || entity instanceof Projectile) {
                entity.setDeltaMovement(moveVec.x, 0.5D, moveVec.z);
                bouncedEntities++;
            }

            if(entity instanceof Player pushedPlayer && pushedPlayer != player && !entity.isShiftKeyDown()) {
                pushedPlayer.setDeltaMovement(moveVec.x, 0.5D, moveVec.z);
                bouncedEntities += 2;
            }
        }
        return bouncedEntities;
    }

    private AABB getEffectAABB(Player player) {
        double range = 4.0D;
        double radius = 2.0D;
        Vec3 srcVec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 lookVec = player.getLookAngle().scale(range);
        Vec3 destVec = srcVec.add(lookVec.x, lookVec.y, lookVec.z);
        return new AABB(destVec.x - radius, destVec.y - radius, destVec.z - radius, destVec.x + radius, destVec.y + radius, destVec.z + radius);
    }


}