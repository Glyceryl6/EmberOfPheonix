package com.glyceryl.emberphoenix.common.items;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class EPShovel extends ShovelItem {

    public EPShovel(Tier tier, float p_43115_, float p_43116_, Properties properties) {
        super(tier, p_43115_, p_43116_, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(this.blocks) ? (state.is(EPBlocks.ASH_BLOCK.get()) ? this.speed*10 : this.speed) : 1.0F;
    }

}
