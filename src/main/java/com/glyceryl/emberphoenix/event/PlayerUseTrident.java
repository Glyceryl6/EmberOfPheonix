package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerUseTrident {

    @SubscribeEvent
    public void playerUseItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.TRIDENT)) {
            if (EPEnchantHelper.getHeatWave(itemstack) > 0 && !isInLavaOrNoRain(player)) {
                event.setResult(Event.Result.DENY);
            } else {
                player.startUsingItem(hand);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerReleasingUsingItem(LivingEntityUseItemEvent.Stop event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            ItemStack itemStack = player.getItemInHand(hand);
            int n = player.getUseItemRemainingTicks();
            if (itemStack.is(Items.TRIDENT)) {
                if (itemStack.getUseDuration() - n >= 10) {
                    int j = EnchantmentHelper.getRiptide(itemStack);
                    int k = EPEnchantHelper.getHeatWave(itemStack);
                    if (k <= 0 || this.isInLavaOrNoRain(player)) {
                        this.flyWithTrident(player.level, player, k);
                        if (!player.level.isClientSide && j > 0) {
                            itemStack.hurtAndBreak(1, player, (player1) ->
                            player1.broadcastBreakEvent(player.getUsedItemHand()));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTridentSpawn(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ThrownTrident trident) {
            ItemStack itemStack = trident.getPickupItem();
            if (EPEnchantHelper.getHeatWave(itemStack) > 0) {
                event.setCanceled(true);
            }
        }
    }

    //判断玩家是否处在岩浆或无雨的地面
    public boolean isInLavaOrNoRain(Player player) {
        return player.isInLava() || !player.isInRain();
    }

    //使玩家能够破空飞行
    private void flyWithTrident(Level level, Player player, int n) {
        player.awardStat(Stats.ITEM_USED.get(Items.TRIDENT));
        if (n > 0) {
            float f7 = player.getYRot();
            float f = player.getXRot();
            float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
            float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
            float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
            float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
            float f5 = 3.0F * ((1.0F + (float)n) / 4.0F);
            f1 *= f5 / f4;
            f2 *= f5 / f4;
            f3 *= f5 / f4;
            player.push(f1, f2, f3);
            player.startAutoSpinAttack(20);
            if (player.isOnGround()) {
                player.move(MoverType.SELF, new Vec3(0.0D, 1.2F, 0.0D));
            }

            SoundEvent soundevent;
            if (n >= 3) {
                soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
            } else if (n == 2) {
                soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
            } else {
                soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
            }

            level.playSound(null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

}