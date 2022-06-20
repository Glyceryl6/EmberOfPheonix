package com.glyceryl.emberphoenix.common.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.Random;

public class PetrifiedMushroom extends AbstractHugeMushroomFeature {

    public PetrifiedMushroom(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }
    
    @Override
    protected void makeCap(LevelAccessor accessor, Random random, BlockPos blockPos, int p_65107_, BlockPos.MutableBlockPos p_65108_, HugeMushroomFeatureConfiguration p_65891_) {
        int i = p_65891_.foliageRadius;

    }
    
    @Override
    protected int getTreeRadiusForHeight(int p_65881_, int p_65882_, int p_65883_, int p_65884_) {
        return p_65884_ <= 3 ? 0 : p_65883_;
    }
}
