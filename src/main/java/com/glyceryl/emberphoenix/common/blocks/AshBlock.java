package com.glyceryl.emberphoenix.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("all")
public class AshBlock extends FallingBlock {

    public AshBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        entity.makeStuckInBlock(blockState, new Vec3(0.25D, 0.05F, 0.25D));
        super.stepOn(level, blockPos, blockState, entity);
    }

}