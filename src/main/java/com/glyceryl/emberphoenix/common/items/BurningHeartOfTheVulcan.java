package com.glyceryl.emberphoenix.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BurningHeartOfTheVulcan extends Item {

    public BurningHeartOfTheVulcan(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}