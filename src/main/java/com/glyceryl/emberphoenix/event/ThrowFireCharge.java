package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.entity.projectile.FallingFireball;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class ThrowFireCharge {

    @SubscribeEvent
    public void onUseFireCharge(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        Random random = player.getRandom();
        Level level = player.getLevel();
        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.FIRE_CHARGE)) {
            float pitch = 1.0F / (random.nextFloat() * 0.4F + 0.8F);
            player.playSound(SoundEvents.SNOWBALL_THROW, 1.0F, pitch);
            if (!level.isClientSide) {
                float xRot = player.getXRot();
                float yRot = player.getYRot();
                FallingFireball fireball = new FallingFireball(level, player);
                fireball.setItem(itemStack);
                fireball.setOwner(player);
                fireball.shootFromRotation(player, xRot, yRot, 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(fireball);
            }
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            player.swing(hand);
        }
    }

}