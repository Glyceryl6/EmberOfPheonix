package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.items.EPShovel;
import com.glyceryl.emberphoenix.common.items.Spark;
import com.glyceryl.emberphoenix.data.EPItemTier;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPItems {

    //Registry new materials.
    public static final RegistryObject<Item> SILVER_INGOT = normal("silver_ingot");
    public static final RegistryObject<Item> RAW_SILVER = normal("raw_silver");
    public static final RegistryObject<Item> BLAZE_RUBY = normal("blaze_ruby");
    public static final RegistryObject<Item> EMBERIUM_INGOT = normal("emberium_ingot");
    //Registry new tools.
    public static final RegistryObject<Item> BLAZE_SWORD = EPBase.ITEMS.register("blaze_sword", () -> new SwordItem(EPItemTier.BLAZE, 3,-2.4F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_SHOVEL = EPBase.ITEMS.register("blaze_shovel", () -> new EPShovel(EPItemTier.BLAZE, 1.5F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_PICKAXE = EPBase.ITEMS.register("blaze_pickaxe", () -> new PickaxeItem(EPItemTier.BLAZE, 1,-2.8F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_AXE = EPBase.ITEMS.register("blaze_axe", () -> new AxeItem(EPItemTier.BLAZE, 6.0F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> BLAZE_HOE = EPBase.ITEMS.register("blaze_hoe", () -> new HoeItem(EPItemTier.BLAZE, 0,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_SWORD = EPBase.ITEMS.register("ember_sword", () -> new SwordItem(EPItemTier.EMBER, 3,-2.4F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_SHOVEL = EPBase.ITEMS.register("ember_shovel", () -> new EPShovel(EPItemTier.EMBER, 1.5F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_PICKAXE = EPBase.ITEMS.register("ember_pickaxe", () -> new PickaxeItem(EPItemTier.EMBER, 1,-2.8F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_AXE = EPBase.ITEMS.register("ember_axe", () -> new AxeItem(EPItemTier.EMBER, 5.0F,-3.0F, defaultBuilder()));
    public static final RegistryObject<Item> EMBER_HOE = EPBase.ITEMS.register("ember_hoe", () -> new HoeItem(EPItemTier.EMBER, -4,0.0F, defaultBuilder()));
    //Registry new armor.

    //Registry special items.
    public static final RegistryObject<Item> SPARK = EPBase.ITEMS.register("spark", () -> new Spark(defaultBuilder()));

    private static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(EPBase.EP_TAB);
    }

    private static RegistryObject<Item> normal(String name) {
        return EPBase.ITEMS.register(name, () -> new Item(defaultBuilder()));
    }

    public static void register(IEventBus eventBus) {
        EPBase.ITEMS.register(eventBus);
    }

}