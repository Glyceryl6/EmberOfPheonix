package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
            float f6 = (float)getMagmaStrider(living);
            AttributeInstance gravity = living.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            boolean flag = living.getFluidHeight(FluidTags.LAVA) > living.getFluidJumpThreshold();
            FluidState fluidstate = living.level.getFluidState(living.blockPosition());
            if (living.isInLava() && living.isAffectedByFluids() && !living.canStandOnFluid(fluidstate) && f6 > 0.0F) {
                float ySpeed;
                float f4 = 0.9F;
                float f5 = 0.02F;
                double d0 = gravity.getValue();
                double d9 = living.getY();
                if (f6 > 3.0F) {
                    f6 = 3.0F;
                }
                if (living.isCrouching()) {
                    ySpeed = -f6 / 3.0F;
                } else if (living.jumping && flag) {
                    ySpeed = f6 / 3.0F;
                } else {
                    ySpeed = living.yya;
                }
                Vec3 vec3 = new Vec3(living.xxa, ySpeed, living.zza);
                living.clearFire();

                f4 += (0.54600006F - f4) * f6 / 3.0F;
                f5 += (living.getSpeed() - f5) * f6;
                f5 *= (float) Objects.requireNonNull(living.getAttribute(ForgeMod.SWIM_SPEED.get())).getValue();

                living.moveRelative(f5, vec3);
                Vec3 vec36 = living.getDeltaMovement();
                if (living.horizontalCollision && living.onClimbable()) {
                    vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
                }

                living.setDeltaMovement(vec36.multiply(f4, 0.8F, f4));
                Vec3 vec32 = living.getFluidFallingAdjustedMovement(d0, flag, living.getDeltaMovement());
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