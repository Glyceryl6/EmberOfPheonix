package com.glyceryl.emberphoenix.common.world.features;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TripleLayerAshFeature extends Feature<NoneFeatureConfiguration> {

    public TripleLayerAshFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPos1 = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
                int k = blockpos.getX() + i;
                int l = blockpos.getZ() + j;
                int i1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                mutableBlockPos.set(k, i1, l);
                mutableBlockPos1.set(mutableBlockPos).move(Direction.DOWN, 3);
                if (this.shouldSnow(worldgenlevel, mutableBlockPos)) {
                    worldgenlevel.setBlock(mutableBlockPos, EPBlocks.ASH_BLOCK.get().defaultBlockState(), 2);
                }
            }
        }

        return true;
    }

    public boolean shouldSnow(LevelReader reader, BlockPos pos) {
        if (pos.getY() >= reader.getMinBuildHeight() && pos.getY() < reader.getMaxBuildHeight()) {
            BlockState blockstate = reader.getBlockState(pos);
            return blockstate.isAir();
        }

        return false;
    }
}
