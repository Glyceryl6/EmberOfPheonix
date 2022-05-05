package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class EPBase {

    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<Biome> BIOMES = create(ForgeRegistries.BIOMES);
    public static final DeferredRegister<EntityType<?>> ENTITY = create(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<BlockEntityType<?>> CONTAINER = create(ForgeRegistries.BLOCK_ENTITIES);

    private static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, EmberOfPhoenix.MOD_ID);
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    public static final CreativeModeTab EP_TAB = new CreativeModeTab("ep_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(EPItems.SPARK.get());
        }
    };
}