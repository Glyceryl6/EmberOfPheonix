package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.enchantments.HeatWave;
import com.glyceryl.emberphoenix.common.enchantments.MagmaStrider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPEnchantments {

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static RegistryObject<Enchantment> MAGMA_STRIDER = RegistryBase.ENCHANTMENTS.register("magma_strider", () -> new MagmaStrider(Enchantment.Rarity.RARE, ARMOR_SLOTS));
    public static RegistryObject<Enchantment> HEAT_WAVE = RegistryBase.ENCHANTMENTS.register("heat_wave", () -> new HeatWave(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        RegistryBase.ENCHANTMENTS.register(eventBus);
    }

}