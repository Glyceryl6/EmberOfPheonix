package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.items.Spark;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPItems {

    //Registry ingot and other ore materials.
    public static final RegistryObject<Item> SCARLET_COAL = EPBase.ITEMS.register("scarlet_coal", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SCARLET_COPPER_INGOT = EPBase.ITEMS.register("scarlet_copper_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SCARLET_IRON_INGOT = EPBase.ITEMS.register("scarlet_iron_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SCARLET_GOLD_INGOT = EPBase.ITEMS.register("scarlet_gold_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SCARLET_SILVER_INGOT = EPBase.ITEMS.register("scarlet_silver_ingot", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> SCARLET_DIAMOND_INGOT = EPBase.ITEMS.register("scarlet_diamond", () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> EMBERIUM_INGOT = EPBase.ITEMS.register("emberium_ingot", () -> new Item(defaultBuilder()));
    //Registry the new tools.

    //Registry the special items.
    public static final RegistryObject<Item> SPARK = EPBase.ITEMS.register("spark", () -> new Spark(defaultBuilder()));
    private static Item.Properties defaultBuilder() {
        return new Item.Properties().tab(EPBase.EP_TAB);
    }

    public static void register(IEventBus eventBus) {
        EPBase.ITEMS.register(eventBus);
    }

}