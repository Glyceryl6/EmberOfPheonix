package com.glyceryl.emberphoenix.event;

import com.glyceryl.emberphoenix.registry.EPEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerUseTrident {

    @SubscribeEvent
    public void playerUseItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        InteractionHand hand = player.getUsedItemHand();
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.TRIDENT)) {
            if (this.getHeatWave(itemstack) > 0 && !this.isInLavaOrNoRain(player)) {
                event.setResult(Event.Result.DENY);
            } else {
                player.startUsingItem(hand);
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    public boolean isInLavaOrNoRain(Player player) {
        return player.isInLava() || !player.isInRain();
    }

    private int getHeatWave(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(EPEnchantments.HEAT_WAVE.get(), stack);
    }

}