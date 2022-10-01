package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentItem.class)
public class MixinTridentItem extends Item {

    public MixinTridentItem(Properties properties) {
        super(properties);
    }

    @Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.BEFORE), cancellable = true)
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {
        if (EPEnchantHelper.getHeatWave(stack) > 0) {
            ci.cancel();
        }
    }

}