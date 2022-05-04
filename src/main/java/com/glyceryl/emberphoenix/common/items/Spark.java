package com.glyceryl.emberphoenix.common.items;

import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.ItemStack;

public class Spark extends FireChargeItem {

    public Spark(Properties properties) {
        super(properties);
    }

    public boolean isFoil(ItemStack stack) {
        return true;
    }

}