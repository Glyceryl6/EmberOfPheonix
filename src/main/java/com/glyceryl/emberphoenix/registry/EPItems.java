package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.items.*;
import com.glyceryl.emberphoenix.data.EPItemTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class EPItems {

    //注册一般的物品
    public static final RegistryObject<Item> TUMBLE_SEED = normal("tumbleseed");
    //注册矿物对应的金属
    public static final RegistryObject<Item> SILVER_INGOT = normal("silver_ingot");
    public static final RegistryObject<Item> RAW_SILVER = normal("raw_silver");
    public static final RegistryObject<Item> BLAZE_RUBY = normal("blaze_ruby");
    public static final RegistryObject<Item> EMBERIUM_INGOT = normal("emberium_ingot");
    //注册新的工具
    public static final RegistryObject<Item> BLAZE_SWORD = RegistryBase.ITEMS.register("blaze_sword", () -> new SwordItem(EPItemTier.BLAZE, 3,-2.4F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_SHOVEL = RegistryBase.ITEMS.register("blaze_shovel", () -> new EPShovel(EPItemTier.BLAZE, 1.5F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_PICKAXE = RegistryBase.ITEMS.register("blaze_pickaxe", () -> new PickaxeItem(EPItemTier.BLAZE, 1,-2.8F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_AXE = RegistryBase.ITEMS.register("blaze_axe", () -> new AxeItem(EPItemTier.BLAZE, 6.0F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_HOE = RegistryBase.ITEMS.register("blaze_hoe", () -> new HoeItem(EPItemTier.BLAZE, 0,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_SWORD = RegistryBase.ITEMS.register("ember_sword", () -> new SwordItem(EPItemTier.EMBER, 3,-2.4F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_SHOVEL = RegistryBase.ITEMS.register("ember_shovel", () -> new EPShovel(EPItemTier.EMBER, 1.5F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_PICKAXE = RegistryBase.ITEMS.register("ember_pickaxe", () -> new PickaxeItem(EPItemTier.EMBER, 1,-2.8F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_AXE = RegistryBase.ITEMS.register("ember_axe", () -> new AxeItem(EPItemTier.EMBER, 5.0F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_HOE = RegistryBase.ITEMS.register("ember_hoe", () -> new HoeItem(EPItemTier.EMBER, -4,0.0F, defaultBuilder()));
    //注册新的盔甲
    public static final RegistryObject<Item> EMBER_HELMET = RegistryBase.ITEMS.register("ember_helmet", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.HEAD, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_CHESTPLATE = RegistryBase.ITEMS.register("ember_chestplate", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.CHEST, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_LEGGINGS = RegistryBase.ITEMS.register("ember_leggings", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.LEGS, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_BOOTS = RegistryBase.ITEMS.register("ember_boots", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.FEET, defaultBuilder()));
    //注册带有特殊功能的物品
    public static final RegistryObject<Item> SPARK = RegistryBase.ITEMS.register("spark", () -> new Spark(defaultBuilder()));

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(RegistryBase.EP_TAB);
    }

    private static RegistryObject<Item> normal(String name) {
        return RegistryBase.ITEMS.register(name, () -> new Item(defaultBuilder()));
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.ITEMS.register(eventBus);
    }

}