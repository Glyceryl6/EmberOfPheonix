package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.entity.projectile.SmallCrack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class ProjectileExplosion {

    @SubscribeEvent
    public void onSmallCrackExplode(ExplosionEvent.Detonate event) {
        Random random = new Random();
        Entity exploder = event.getExplosion().getExploder();
        if (exploder instanceof SmallCrack smallCrack) {
            Level level = smallCrack.level;
            for (BlockPos blockPos : event.getAffectedBlocks()) {
                BlockState fireBlock = Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, 5);
                boolean isSolidUnder = level.getBlockState(blockPos.below()).isSolidRender(level, blockPos.below());
                if (random.nextInt(3) == 0 && level.getBlockState(blockPos).isAir() && isSolidUnder) {
                    level.setBlockAndUpdate(blockPos, fireBlock);
                }
            }
        }
    }

}
