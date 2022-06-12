package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TridentItem.class)
public class MixinTridentItem {

    @Redirect(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRiptide(Lnet/minecraft/world/item/ItemStack;)I"))
    public int releaseUsing(ItemStack itemStack) {
        return EnchantmentHelper.getRiptide(itemStack) + EPEnchantHelper.getHeatWave(itemStack);
    }

}