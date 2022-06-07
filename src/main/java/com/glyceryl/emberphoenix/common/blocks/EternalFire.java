package com.glyceryl.emberphoenix.common.blocks;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class EternalFire extends BaseFireBlock {

    public EternalFire(Properties properties) {
        super(properties, 4.0F);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        if (!entity.fireImmune()) {
            entity.hurt(DamageSource.IN_FIRE, 4.0F);
        }
    }

    public static BlockState getState(BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos blockpos = blockPos.below();
        BlockState blockstate = blockGetter.getBlockState(blockpos);
        return canSurviveOnBlock(blockstate) ? EPBlocks.ETERNAL_FIRE.get().defaultBlockState() : ((FireBlock)Blocks.FIRE).getStateForPlacement(blockGetter, blockPos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState p_56661_, LevelAccessor levelAccessor, BlockPos pos, BlockPos p_56664_) {
        return this.canSurvive(state, levelAccessor, pos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSurviveOnBlock(levelReader.getBlockState(blockPos.below()));
    }

    private static boolean canSurviveOnBlock(BlockState blockState) {
        return blockState.is(EPBlocks.ETERNAL_FIRE_ALTAR.get());
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return true;
    }

}