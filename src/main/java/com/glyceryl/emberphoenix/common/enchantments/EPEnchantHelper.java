package com.glyceryl.emberphoenix.common.enchantments;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EPEnchantHelper {

    public static int getHeatWave(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(EPEnchantments.HEAT_WAVE.get(), stack);
    }

    public static int getMagmaStrider(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(EPEnchantments.MAGMA_STRIDER.get(), entity);
    }

}
