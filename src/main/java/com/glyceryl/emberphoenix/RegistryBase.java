package com.glyceryl.emberphoenix;

import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class RegistryBase {

    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<Biome> BIOMES = create(ForgeRegistries.BIOMES);
    public static final DeferredRegister<SoundEvent> SOUNDS = create(ForgeRegistries.SOUND_EVENTS);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = create(ForgeRegistries.ENCHANTMENTS);
    public static final DeferredRegister<Feature<?>> FEATURES = create(ForgeRegistries.FEATURES);
    public static final DeferredRegister<EntityType<?>> ENTITY = create(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<BlockEntityType<?>> CONTAINER = create(ForgeRegistries.BLOCK_ENTITIES);

    private static <B extends IForgeRegistryEntry<B>> DeferredRegister<B> create(IForgeRegistry<B> reg) {
        return DeferredRegister.create(reg, EmberOfPhoenix.MOD_ID);
    }

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().tab(tab)));
        return toReturn;
    }

    public static <T extends Block> RegistryObject<T> registerFireProofBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().fireResistant().tab(tab)));
        return toReturn;
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(String key, F value) {
        value.setRegistryName(new ResourceLocation(EmberOfPhoenix.MOD_ID, key));
        ForgeRegistries.FEATURES.register(value);
        return value;
    }

    public static final CreativeModeTab EP_TAB = new CreativeModeTab("ep_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(EPItems.SPARK.get());
        }
    };
}