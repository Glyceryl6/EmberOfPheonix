package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class LivingWalkOnMagma {

    @SubscribeEvent
    public void onLivingTravel(LivingEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof Player) {
            boolean flag = living.getDeltaMovement().y <= 0.0D;
            Vec3 vec3 = new Vec3(living.xxa, living.yya, living.zza);
            float f6 = (float)getMagmaStrider(living);
            FluidState fluidstate = living.level.getFluidState(living.blockPosition());
            if (living.isInLava() && living.isAffectedByFluids() && !living.canStandOnFluid(fluidstate) && f6 > 0.0F) {
                living.clearFire();
                double d9 = living.getY();
                float f4 = living.isSprinting() ? 0.9F : 0.8F;
                float f5 = 0.02F;
                if (f6 > 3.0F) {
                    f6 = 3.0F;
                }
                if (!living.isOnGround()) {
                    f6 *= 0.5F;
                }
                if (f6 > 0.0F) {
                    f4 += (0.54600006F - f4) * f6 / 3.0F;
                    f5 += (living.getSpeed() - f5) * f6 / 3.0F;
                }
                f5 *= (float) Objects.requireNonNull(living.getAttribute(ForgeMod.SWIM_SPEED.get())).getValue();
                living.moveRelative(f5, vec3);
                Vec3 vec36 = living.getDeltaMovement();
                if (living.horizontalCollision && living.onClimbable()) {
                    vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
                }

                living.setDeltaMovement(vec36.multiply(f4, 0.8F, f4));
                Vec3 vec32 = living.getFluidFallingAdjustedMovement(0.08D, flag, living.getDeltaMovement());
                living.setDeltaMovement(vec32);
                if (living.horizontalCollision && living.isFree(vec32.x, vec32.y + (double)0.6F - living.getY() + d9, vec32.z)) {
                    living.setDeltaMovement(vec32.x, 0.3F, vec32.z);
                }
            }
        }
    }

    public static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}