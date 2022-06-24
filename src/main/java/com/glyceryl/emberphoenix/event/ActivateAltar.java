package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.entity.projectile.GatewayCreator;
import com.glyceryl.emberphoenix.common.entity.projectile.PhoenixGateway;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ActivateAltar {

    @SubscribeEvent
    public void onUseDiamondOnAltar(PlayerInteractEvent.RightClickBlock event) {
        int range = 2;
        Level level = event.getWorld();
        Player player = event.getPlayer();
        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemStack = player.getItemInHand(hand);
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (itemStack.is(Items.DIAMOND) && blockState.is(EPBlocks.ETERNAL_FIRE_ALTAR.get())) {
            Direction direction = event.getHitVec().getDirection();
            blockPos = blockPos.relative(direction);
            boolean isUp = direction == Direction.UP;
            boolean isAir = level.getBlockState(blockPos).isAir();
            boolean onlyOneDiamond = true;
            boolean hasEnoughSolidBlock = true;
            //检测玩家是否是对着祭坛的顶部使用钻石且该顶部方块为空气
            if (!level.isClientSide && isAir && isUp) {
                BlockPos centerPos = new BlockPos(Vec3.atCenterOf(blockPos));
                AABB aabb = (new AABB(centerPos)).inflate(2);
                //获取以中心祭坛为中心 5×5×5 区域内特定实体的数量
                int creatorCount = level.getEntitiesOfClass(GatewayCreator.class, aabb).size();
                int gatewayCount = level.getEntitiesOfClass(PhoenixGateway.class, aabb).size();
                //检测祭坛周围区域是否已存在创建器实体或传送门实体，没有则继续
                if (creatorCount <= 0 && gatewayCount <= 0) {
                    List<BlockPos> baseBlockCount = new ArrayList<>(List.of());
                    BlockPos downPos = centerPos.relative(Direction.DOWN);
                    //检测召唤区域内是否还存在其它的无关方块
                    for (BlockPos pos : BlockPos.withinManhattan(downPos, range, 1, range)) {
                        if (!level.getBlockState(pos).isAir()) {
                            baseBlockCount.add(pos);
                        }
                    }
                    //检测四个角是否均存在永恒之火
                    Block fire = EPBlocks.ETERNAL_FIRE.get();
                    BlockState state1 = level.getBlockState(centerPos.relative(Direction.EAST, 2).relative(Direction.NORTH, 2));
                    BlockState state2 = level.getBlockState(centerPos.relative(Direction.EAST, 2).relative(Direction.SOUTH, 2));
                    BlockState state3 = level.getBlockState(centerPos.relative(Direction.WEST, 2).relative(Direction.NORTH, 2));
                    BlockState state4 = level.getBlockState(centerPos.relative(Direction.WEST, 2).relative(Direction.SOUTH, 2));
                    boolean hasEternalFire = state1.is(fire) && state2.is(fire) && state3.is(fire) && state4.is(fire);
                    //满足剩余所有条件后，召唤一个传送门创建器
                    if (baseBlockCount.size() == 9 && hasEternalFire) {
                        GatewayCreator creator = new GatewayCreator(EPEntity.GATEWAY_CREATOR.get(), level);
                        creator.setPos(Vec3.atCenterOf(blockPos));
                        creator.setActivated(true);
                        level.addFreshEntity(creator);
                    } else {
                        hasEnoughSolidBlock = false;
                    }
                } else {
                    onlyOneDiamond = false;
                }
            }
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!player.getAbilities().instabuild && isUp && onlyOneDiamond && hasEnoughSolidBlock) {
                itemStack.shrink(1);
            }
            player.swing(hand);
        }
    }

}