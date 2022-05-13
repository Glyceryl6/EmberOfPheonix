package com.glyceryl.emberphoenix.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EternalFireAltar extends Block {

    private static final VoxelShape UP = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN = Block.box(1.0D, 0.0D, 1.0D, 14.0D, 1.0D, 14.0D);
    private static final VoxelShape FINAL = Shapes.or(UP, DOWN);

    public EternalFireAltar(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("all")
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return FINAL;
    }
}