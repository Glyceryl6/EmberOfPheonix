package com.glyceryl.emberphoenix.data;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class EPItemTier {

    public static final Tier BLAZE = TierSortingRegistry.registerTier(
            new ForgeTier(4, 500, 12.0F, 6.0F, 15, BlockTags.create(EmberOfPhoenix.prefix("needs_blaze_tool")),
            () -> Ingredient.of(EPItems.BLAZE_RUBY.get())), EmberOfPhoenix.prefix("blaze"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));

    public static final Tier EMBER = TierSortingRegistry.registerTier(
            new ForgeTier(4, 300, 8.0F, 3.0F, 10, BlockTags.create(EmberOfPhoenix.prefix("needs_ember_tool")),
            () -> Ingredient.of(EPItems.EMBERIUM_INGOT.get())), EmberOfPhoenix.prefix("ember"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));

}