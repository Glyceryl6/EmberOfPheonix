package com.glyceryl.emberphoenix.common.blocks;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("all")
public class EternalFire extends BaseFireBlock {

    public EternalFire(Properties properties) {
        super(properties, 3.0F);
    }

    public static BlockState getState(BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockpos = blockPos.below();
        BlockState blockstate = blockGetter.getBlockState(blockpos);
        return EPBlocks.ETERNAL_FIRE.get().defaultBlockState();
    }

    public BlockState updateShape(BlockState p_60541_, Direction direction, BlockState p_56661_, LevelAccessor levelAccessor, BlockPos p_56663_, BlockPos p_56664_) {
        return this.canSurvive(p_60541_, levelAccessor, p_56663_) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSurviveOnBlock(levelReader.getBlockState(blockPos.below()));
    }

    public static boolean canSurviveOnBlock(BlockState blockState) {
        return blockState.is(EPBlocks.ETERNAL_FIRE_ALTAR.get());
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }
}