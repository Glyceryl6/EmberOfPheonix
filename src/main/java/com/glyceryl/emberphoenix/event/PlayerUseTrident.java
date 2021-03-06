package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.common.enchantments.EPEnchantHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
            int i = itemStack.getUseDuration() - n;
            if (itemStack.is(Items.TRIDENT)) {
                if (i >= 10) {
                    int k = EPEnchantHelper.getHeatWave(itemStack);
                    if (k <= 0 || this.isInLavaOrNoRain(player)) {
                        this.judgeEnvironment(itemStack, player.level, livingEntity, player, k);
                    }
                }
            }
        }
    }

    public boolean isInLavaOrNoRain(Player player) {
        return player.isInLava() || !player.isInRain();
    }

    public void judgeEnvironment(ItemStack itemStack, Level level, LivingEntity livingEntity, Player player, int n) {
        int j = EnchantmentHelper.getRiptide(itemStack);
        int k = EPEnchantHelper.getHeatWave(itemStack);
        if (!level.isClientSide) {
            itemStack.hurtAndBreak(1, player, (event) -> event.broadcastBreakEvent(livingEntity.getUsedItemHand()));
            if (j == 0 && k == 0) {
                ThrownTrident thrownTrident = new ThrownTrident(level, player, itemStack);
                thrownTrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float)n * 0.5F, 1.0F);
                if (player.getAbilities().instabuild) {
                    thrownTrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(thrownTrident);
                level.playSound(null, thrownTrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(itemStack);
                }
            }
        }

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
                player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0D));
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