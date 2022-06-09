package com.glyceryl.emberphoenix.common.world.ore;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class EPOrePlacements {

    public static final Holder<PlacedFeature> ORE_PHOENIX_COAL = register("ore_phoenix_coal", EPOreFeatures.ORE_PHOENIX_COAL, 11, -54, -20);
    public static final Holder<PlacedFeature> ORE_PHOENIX_IRON = register("ore_phoenix_iron", EPOreFeatures.ORE_PHOENIX_IRON, 10, -54, -20);
    public static final Holder<PlacedFeature> ORE_PHOENIX_COPPER = register("ore_phoenix_copper", EPOreFeatures.ORE_PHOENIX_COPPER, 10, -64, -20);
    public static final Holder<PlacedFeature> ORE_PHOENIX_SILVER = register("ore_phoenix_silver", EPOreFeatures.ORE_PHOENIX_SILVER, 5, -60, -32);
    public static final Holder<PlacedFeature> ORE_PHOENIX_GOLD = register("ore_phoenix_gold", EPOreFeatures.ORE_PHOENIX_GOLD, 6, -60, -32);
    public static final Holder<PlacedFeature> ORE_PHOENIX_DIAMOND = register("ore_phoenix_diamond", EPOreFeatures.ORE_PHOENIX_DIAMOND, 5, -60, -44);
    public static final Holder<PlacedFeature> ORE_PHOENIX_HARDSLATE = register("ore_phoenix_hardslate", EPOreFeatures.ORE_PHOENIX_HARDSLATE, 4, -64, -30);
    public static final Holder<PlacedFeature> ORE_PHOENIX_SMOOTH_BASALT = register("ore_phoenix_smooth_basalt", EPOreFeatures.ORE_PHOENIX_SMOOTH_BASALT, 3, -64, -30);
    public static final Holder<PlacedFeature> ORE_ASH_BLOCK = register("ore_ash_block", EPOreFeatures.ORE_ASH_BLOCK, 30, -64, -24);

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int i, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(i), modifier);
    }

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> holder, int size, int min, int max) {
        return PlacementUtils.register(name, holder, commonOrePlacement(size, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min), VerticalAnchor.belowTop(max))));
    }

}