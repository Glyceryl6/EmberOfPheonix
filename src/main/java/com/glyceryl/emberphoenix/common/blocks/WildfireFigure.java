package com.glyceryl.emberphoenix.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildfireFigure extends Block {

    private static final VoxelShape VOXEL_SHAPE = Shapes.or(
            Block.box(4, 18, 4, 12, 26, 12),
            Block.box(3.5, 18, 3.5, 12.5, 27, 12.5),
            Block.box(-2, 0, 3, 0, 17, 13),
            Block.box(3, 0, 16, 13, 17, 18),
            Block.box(16, 0, 3, 18, 17, 13),
            Block.box(3, 0, -2, 13, 17, 0),
            Block.box(6, -1, 6, 10, 17, 10));

    public WildfireFigure(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

}