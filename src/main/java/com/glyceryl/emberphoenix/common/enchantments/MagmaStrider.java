package com.glyceryl.emberphoenix.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MagmaStrider extends Enchantment {

    public MagmaStrider(Rarity rarity, EquipmentSlot[] slots) {
        super(rarity, EnchantmentCategory.ARMOR_FEET, slots);
    }

    public int getMinCost(int i) {
        return i * 10;
    }

    public int getMaxCost(int i) {
        return this.getMinCost(i) + 15;
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}