package com.glyceryl.emberphoenix.common.world.ore;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class EPOrePlacements {

    public static final Holder<PlacedFeature> ORE_PHOENIX_COAL = register("ore_phoenix_coal", EPOreFeatures.ORE_PHOENIX_COAL, 11, 12, 45);
    public static final Holder<PlacedFeature> ORE_PHOENIX_IRON = register("ore_phoenix_iron", EPOreFeatures.ORE_PHOENIX_IRON, 10, 12, 45);
    public static final Holder<PlacedFeature> ORE_PHOENIX_COPPER = register("ore_phoenix_copper", EPOreFeatures.ORE_PHOENIX_COPPER, 10, 0, 45);
    public static final Holder<PlacedFeature> ORE_PHOENIX_SILVER = register("ore_phoenix_silver", EPOreFeatures.ORE_PHOENIX_SILVER, 5, 0, 20);
    public static final Holder<PlacedFeature> ORE_PHOENIX_GOLD = register("ore_phoenix_gold", EPOreFeatures.ORE_PHOENIX_GOLD, 6, 0, 32);
    public static final Holder<PlacedFeature> ORE_PHOENIX_DIAMOND = register("ore_phoenix_diamond", EPOreFeatures.ORE_PHOENIX_DIAMOND, 5, 0, 20);
    public static final Holder<PlacedFeature> ORE_PHOENIX_HARDSLATE = register("ore_phoenix_hardslate", EPOreFeatures.ORE_PHOENIX_HARDSLATE, 3, 0, 45);
    public static final Holder<PlacedFeature> ORE_PHOENIX_SMOOTH_BASALT = register("ore_phoenix_smooth_basalt", EPOreFeatures.ORE_PHOENIX_SMOOTH_BASALT, 3, 0, 45);
    public static final Holder<PlacedFeature> ORE_PHOENIX_AMETHYST = register("ore_phoenix_amethyst", EPOreFeatures.ORE_PHOENIX_AMETHYST, 2, 0, 16);
    public static final Holder<PlacedFeature> ORE_PHOENIX_DENSE_GLOWSTONE = register("ore_phoenix_dense_glowstone", EPOreFeatures.ORE_PHOENIX_DENSE_GLOWSTONE, 6, 0, 40);
    public static final Holder<PlacedFeature> ORE_ASH_BLOCK = register("ore_ash_block", EPOreFeatures.ORE_ASH_BLOCK, 30, 0, 40);

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