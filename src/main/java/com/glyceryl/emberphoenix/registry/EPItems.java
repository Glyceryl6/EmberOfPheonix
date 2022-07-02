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
    public static final RegistryObject<Item> BLAZE_RUBY = RegistryBase.ITEMS.register("blaze_ruby", () -> new Item(fireProof()));
    public static final RegistryObject<Item> EMBERIUM_INGOT = RegistryBase.ITEMS.register("emberium_ingot", () -> new Item(fireProof()));
    //注册新的工具
    public static final RegistryObject<Item> BLAZE_HOE = RegistryBase.ITEMS.register("blaze_hoe", () -> new HoeItem(EPItemTier.BLAZE, 0,-3.0F, fireProof()));
    public static final RegistryObject<Item> BLAZE_AXE = RegistryBase.ITEMS.register("blaze_axe", () -> new AxeItem(EPItemTier.BLAZE, 6.0F,-3.0F, fireProof()));
    public static final RegistryObject<Item> BLAZE_SWORD = RegistryBase.ITEMS.register("blaze_sword", () -> new SwordItem(EPItemTier.BLAZE, 3,-2.4F, fireProof()));
    public static final RegistryObject<Item> BLAZE_SHOVEL = RegistryBase.ITEMS.register("blaze_shovel", () -> new EPShovel(EPItemTier.BLAZE, 1.5F,-3.0F, fireProof()));
    public static final RegistryObject<Item> BLAZE_PICKAXE = RegistryBase.ITEMS.register("blaze_pickaxe", () -> new PickaxeItem(EPItemTier.BLAZE, 1,-2.8F, fireProof()));
    public static final RegistryObject<Item> EMBER_PICKAXE = RegistryBase.ITEMS.register("emberium_pickaxe", () -> new PickaxeItem(EPItemTier.EMBER, 1,-2.8F, fireProof()));
    public static final RegistryObject<Item> EMBER_SHOVEL = RegistryBase.ITEMS.register("emberium_shovel", () -> new EPShovel(EPItemTier.EMBER, 1.5F,-3.0F, fireProof()));
    public static final RegistryObject<Item> EMBER_SWORD = RegistryBase.ITEMS.register("emberium_sword", () -> new SwordItem(EPItemTier.EMBER, 3,-2.4F, fireProof()));
    public static final RegistryObject<Item> EMBER_AXE = RegistryBase.ITEMS.register("emberium_axe", () -> new AxeItem(EPItemTier.EMBER, 5.0F,-3.0F, fireProof()));
    public static final RegistryObject<Item> EMBER_HOE = RegistryBase.ITEMS.register("emberium_hoe", () -> new HoeItem(EPItemTier.EMBER, -4,0.0F, fireProof()));
    //注册新的盔甲
    public static final RegistryObject<Item> EMBER_CHESTPLATE = RegistryBase.ITEMS.register("emberium_chestplate", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.CHEST, fireProof()));
    public static final RegistryObject<Item> EMBER_LEGGINGS = RegistryBase.ITEMS.register("emberium_leggings", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.LEGS, fireProof()));
    public static final RegistryObject<Item> EMBER_HELMET = RegistryBase.ITEMS.register("emberium_helmet", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.HEAD, fireProof()));
    public static final RegistryObject<Item> EMBER_BOOTS = RegistryBase.ITEMS.register("emberium_boots", () -> new ArmorItem(EPArmorMaterials.EMBERIUM, EquipmentSlot.FEET, fireProof()));
    //注册新的食物
    public static final RegistryObject<Item> REDSTONE_BERRIES = RegistryBase.ITEMS.register("redstone_berries", () -> new ItemNameBlockItem(EPBlocks.REDSTONE_BERRY_BUSH.get(), defaultBuilder().food(EPFoods.REDSTONE_BERRIES)));
    //注册带有特殊性质的物品
    public static final RegistryObject<Item> SPARK = RegistryBase.ITEMS.register("spark", () -> new Spark(defaultBuilder()));
    public static final RegistryObject<Item> BOOMERANG_FIREBALL = RegistryBase.ITEMS.register("boomerang_fireball", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEAD_HEART_OF_THE_VULCAN = RegistryBase.ITEMS.register("dead_heart_of_the_vulcan", () -> new Item(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DYING_HEART_OF_THE_VULCAN = RegistryBase.ITEMS.register("dying_heart_of_the_vulcan", () -> new Item(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CHARGED_HEART_OF_THE_VULCAN = RegistryBase.ITEMS.register("charged_heart_of_the_vulcan", () -> new Item(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BURNING_HEART_OF_THE_VULCAN = RegistryBase.ITEMS.register("burning_heart_of_the_vulcan", () -> new BurningHeartOfTheVulcan(defaultBuilder().rarity(Rarity.EPIC)));

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(RegistryBase.EP_TAB);
    }

    public static Item.Properties fireProof() {
        return new Item.Properties().tab(RegistryBase.EP_TAB).fireResistant();
    }

    private static RegistryObject<Item> normal(String name) {
        return RegistryBase.ITEMS.register(name, () -> new Item(defaultBuilder()));
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.ITEMS.register(eventBus);
    }

}