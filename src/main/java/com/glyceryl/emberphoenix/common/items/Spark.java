package com.glyceryl.emberphoenix.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Spark extends Item {

    public Spark(Properties properties) {
        super(properties);
    }

    public boolean isFoil(ItemStack stack) {
        return true;
    }

}
