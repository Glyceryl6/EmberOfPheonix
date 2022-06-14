package com.glyceryl.emberphoenix.common.blocks;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class Tumbleweed extends BushBlock {

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");

    public Tumbleweed(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FILLED, Boolean.FALSE));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
        return state.is(EPBlocks.SCARLET_DIRT.get());
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!entity.fireImmune()) {
            entity.makeStuckInBlock(blockState, new Vec3(0.8F, 0.75D, 0.8F));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.STICK) && !state.getValue(FILLED)) {
            level.setBlock(blockPos, state.setValue(FILLED, Boolean.TRUE), 2);
            if (!level.isClientSide) {
                level.levelEvent(1505, blockPos, 50);
            }
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, blockPos, player, hand, result);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
    }

}