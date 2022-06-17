package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {

    @Redirect(method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isSpectator()Z", ordinal = 0, remap = true), remap = false)
    private static boolean setupFog(Entity entity) {
        if (entity instanceof Player player) {
            float f6 = EPEnchantHelper.getMagmaStrider(player);
            if (player.fireImmune() || player.hasEffect(MobEffects.FIRE_RESISTANCE) || f6 > 0.0F) {
                return true;
            }
        }
        return entity.isSpectator();
    }

}