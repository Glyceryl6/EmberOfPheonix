package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.tags.FluidTags;
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
            float f6 = (float)getMagmaStrider(living);
            boolean flag = living.getFluidHeight(FluidTags.LAVA) > living.getFluidJumpThreshold();
            FluidState fluidstate = living.level.getFluidState(living.blockPosition());
            if (living.isInLava() && living.isAffectedByFluids() && !living.canStandOnFluid(fluidstate) && f6 > 0.0F) {
                float ySpeed;
                if (f6 > 3.0F) {
                    f6 = 3.0F;
                }
                if (living.isCrouching()) {
                    ySpeed = -f6 / 2.0F;
                } else if (living.jumping && flag) {
                    ySpeed = f6 / 2.0F;
                } else {
                    ySpeed = living.yya;
                }
                Vec3 vec3 = new Vec3(living.xxa, ySpeed, living.zza);
                living.clearFire();
                float f5 = 0.02F;
                f5 += (living.getSpeed() - f5) * f6 / 6.0F;
                f5 *= (float) Objects.requireNonNull(living.getAttribute(ForgeMod.SWIM_SPEED.get())).getValue();
                living.moveRelative(f5, vec3);
            }
        }
    }

    public static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}