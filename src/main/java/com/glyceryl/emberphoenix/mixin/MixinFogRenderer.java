package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {

    @Redirect(
            method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isSpectator()Z", ordinal = 0), remap = false)
    private static boolean setupFog(Entity entity) {
        if (entity instanceof Player player) {
            float f6 = getMagmaStrider(player);
            if (player.fireImmune() || player.hasEffect(MobEffects.FIRE_RESISTANCE) || f6 > 0.0F) {
                return true;
            }
        }
        return entity.isSpectator();
    }

    private static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}