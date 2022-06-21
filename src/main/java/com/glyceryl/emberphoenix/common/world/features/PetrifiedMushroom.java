package com.glyceryl.emberphoenix.common.world.features;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.Random;

public class PetrifiedMushroom extends AbstractHugeMushroomFeature {
    
    public PetrifiedMushroom(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void placeTrunk(LevelAccessor level, Random random, BlockPos pos, HugeMushroomFeatureConfiguration config, int p_65115_, BlockPos.MutableBlockPos mutableBlockPos) {
        BlockState basalt = Blocks.BASALT.defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        for(int i = 0; i < p_65115_ + 1; ++i) {
            mutableBlockPos.set(pos).move(Direction.UP, i);
            if (!level.getBlockState(mutableBlockPos).isSolidRender(level, mutableBlockPos)) {
                this.setBlock(level, mutableBlockPos, basalt);
                this.setBlock(level, mutableBlockPos.east(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.south(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.west(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.north(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.east().north(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.east().south(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.west().north(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
                this.setBlock(level, mutableBlockPos.west().south(), random.nextInt(10) < 6 || i == 0 ? basalt : air);
            }
        }
    }

    @Override
    protected int getTreeHeight(Random p_65130_) {
        int i = p_65130_.nextInt(7) + 4;
        if (p_65130_.nextInt(12) == 0) {
            i *= 2;
        }

        return i;
    }

    @Override
    protected void makeCap(LevelAccessor level, Random random, BlockPos pos, int p_65889_, BlockPos.MutableBlockPos p_65890_, HugeMushroomFeatureConfiguration config) {
        BlockState state = EPBlocks.HARD_SLATE.get().defaultBlockState();
        int i = config.foliageRadius;
        int l = config.foliageRadius + 1;
        int m = config.foliageRadius - 1;
        //第一层
        for(int j = -l; j <= l; ++j) {
            for(int k = -l; k <= l; ++k) {
                boolean flag = j == -l;
                boolean flag1 = j == l;
                boolean flag2 = k == -l;
                boolean flag3 = k == l;
                boolean flag4 = flag || flag1;
                boolean flag5 = flag2 || flag3;
                if (!flag4 || !flag5) {
                    p_65890_.setWithOffset(pos, j, p_65889_, k);
                    if (!level.getBlockState(p_65890_).isSolidRender(level, p_65890_)) {
                        this.setBlock(level, p_65890_, state);
                    }
                }
            }
        }
        //第二层
        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                boolean flag = j == -i;
                boolean flag1 = j == i;
                boolean flag2 = k == -i;
                boolean flag3 = k == i;
                boolean flag4 = flag || flag1;
                boolean flag5 = flag2 || flag3;
                if (!flag4 || !flag5) {
                    p_65890_.setWithOffset(pos, j, p_65889_ + 1, k);
                    if (!level.getBlockState(p_65890_).isSolidRender(level, p_65890_)) {
                        this.setBlock(level, p_65890_.setWithOffset(pos, j, p_65889_ + 1, k), state);
                        this.setBlock(level, p_65890_.setWithOffset(pos, j, p_65889_, k), Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
        //第三层
        for(int j = -m; j <= m; ++j) {
            for(int k = -m; k <= m; ++k) {
                boolean flag = j == -m;
                boolean flag1 = j == m;
                boolean flag2 = k == -m;
                boolean flag3 = k == m;
                boolean flag4 = flag || flag1;
                boolean flag5 = flag2 || flag3;
                if (!flag4 || !flag5) {
                    p_65890_.setWithOffset(pos, j, p_65889_ + 2, k);
                    if (!level.getBlockState(p_65890_).isSolidRender(level, p_65890_)) {
                        this.setBlock(level, p_65890_.setWithOffset(pos, j, p_65889_ + 2, k), state);
                    }
                }
            }
        }
    }

    @Override
    protected boolean isValidPosition(LevelAccessor level, BlockPos pos, int p_65101_, BlockPos.MutableBlockPos p_65102_, HugeMushroomFeatureConfiguration config) {
        int i = pos.getY();
        if (i >= level.getMinBuildHeight() + 1 && i + p_65101_ + 1 < level.getMaxBuildHeight()) {
            BlockState blockstate = level.getBlockState(pos.below());
            if (!blockstate.is(EPBlocks.SCARLET_STONE.get())) {
                return false;
            } else {
                for(int j = 0; j <= p_65101_; ++j) {
                    int k = this.getTreeRadiusForHeight(-1, -1, config.foliageRadius, j);
                    for(int l = -k; l <= k; ++l) {
                        for(int i1 = -k; i1 <= k; ++i1) {
                            BlockState blockState1 = level.getBlockState(p_65102_.setWithOffset(pos, l, j, i1));
                            if (!blockState1.isAir()) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    protected int getTreeRadiusForHeight(int p_65881_, int p_65882_, int p_65883_, int p_65884_) {
        return p_65884_ <= 6 ? 0 : p_65883_;
    }
    
}
