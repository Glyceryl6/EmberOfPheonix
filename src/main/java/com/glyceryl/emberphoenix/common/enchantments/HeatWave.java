package com.glyceryl.emberphoenix.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.*;

public class HeatWave extends Enchantment {

    public HeatWave(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.TRIDENT, slots);
    }

    public int getMinCost(int i) {
        return 10 + i * 7;
    }

    public int getMaxCost(int i) {
        return 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.LOYALTY && enchantment != Enchantments.CHANNELING;
    }

}