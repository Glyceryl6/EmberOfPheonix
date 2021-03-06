package com.glyceryl.emberphoenix.common.world.ore;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class EPOreFeatures {

    public static final RuleTest SCARLET_STONE = new BlockMatchTest(EPBlocks.SCARLET_STONE.get());

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_COAL = register("ore_phoenix_coal", EPBlocks.SCARLET_COAL_ORE.get(), 12);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_IRON = register("ore_phoenix_iron", EPBlocks.SCARLET_IRON_ORE.get(), 7);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_GOLD = register("ore_phoenix_gold", EPBlocks.SCARLET_GOLD_ORE.get(), 2);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_COPPER = register("ore_phoenix_copper", EPBlocks.SCARLET_COPPER_ORE.get(), 7);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_SILVER = register("ore_phoenix_silver", EPBlocks.SCARLET_SILVER_ORE.get(), 3);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_DIAMOND = register("ore_phoenix_diamond", EPBlocks.SCARLET_DIAMOND_ORE.get(), 2);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_HARDSLATE = register("ore_phoenix_hardslate", EPBlocks.HARD_SLATE.get(), 48);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_PHOENIX_SMOOTH_BASALT = register("ore_phoenix_smooth_basalt", Blocks.SMOOTH_BASALT, 48);
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_ASH_BLOCK = register(SCARLET_STONE, "ore_ash_block", EPBlocks.ASH_BLOCK.get(), 33);

    private static Holder<ConfiguredFeature<OreConfiguration, ?>> register(String name, Block block, int i) {
        return FeatureUtils.register(name, Feature.ORE, new OreConfiguration(SCARLET_STONE, block.defaultBlockState(), i));
    }

    @SuppressWarnings("all")
    private static Holder<ConfiguredFeature<OreConfiguration, ?>> register(RuleTest ruleTest, String name, Block block, int i) {
        return FeatureUtils.register(name, Feature.ORE, new OreConfiguration(ruleTest, block.defaultBlockState(), i));
    }

}
