package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
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

    @SubscribeEvent
    public void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        float f6 = getMagmaStrider((LivingEntity) event.getCamera().getEntity());
        if (event.getCamera().getFluidInCamera() == FogType.WATER) {
            float waterVision = 1.0f;
            if(event.getCamera().getEntity() instanceof LocalPlayer) {
                waterVision = Math.max(0.25f, ((LocalPlayer)event.getCamera().getEntity()).getWaterVision());
            }
            event.scaleFarPlaneDistance(waterVision * 150.0F);
            event.setCanceled(true);
        } else if (event.getCamera().getFluidInCamera() == FogType.LAVA && f6 > 0.0F) {
            event.scaleFarPlaneDistance(30.0F * f6);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void renderOverlay(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.FIRE) {
            return;
        }
        if (event.getPlayer().isCreative()) {
            event.setCanceled(true);
        } else if (getMagmaStrider(event.getPlayer()) > 0.0F && event.getPlayer().isEyeInFluid(FluidTags.LAVA)) {
            event.getPoseStack().translate(0, -0.25, 0);
        }
    }

    public static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}