package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentItem.class)
public class MixinTridentItem extends Item implements Vanishable {

    public MixinTridentItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(itemStack) - n;
            if (i >= 10) {
                int j = EnchantmentHelper.getRiptide(itemStack);
                int k = this.getHeatWave(itemStack);
                if (j <= 0 || player.isInWaterOrRain()) {
                    this.judgeEnvironment(itemStack, level, livingEntity, player, j);
                }
                if (k <= 0 || this.isInLavaOrNoRain(player)) {
                    this.judgeEnvironment(itemStack, level, livingEntity, player, k);
                }
            }
        }
    }

    public boolean isInLavaOrNoRain(Player player) {
        return player.isInLava() || !player.isInRain();
    }

    public void judgeEnvironment(ItemStack itemStack, Level level, LivingEntity livingEntity, Player player, int n) {
        int j = EnchantmentHelper.getRiptide(itemStack);
        int k = this.getHeatWave(itemStack);
        if (!level.isClientSide) {
            itemStack.hurtAndBreak(1, player, (event) -> event.broadcastBreakEvent(livingEntity.getUsedItemHand()));
            if (j == 0 && k == 0) {
                ThrownTrident throwntrident = new ThrownTrident(level, player, itemStack);
                throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float)n * 0.5F, 1.0F);
                if (player.getAbilities().instabuild) {
                    throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(throwntrident);
                level.playSound(null, throwntrident, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(itemStack);
                }
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
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

    private int getHeatWave(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(EPEnchantments.HEAT_WAVE.get(), stack);
    }

}