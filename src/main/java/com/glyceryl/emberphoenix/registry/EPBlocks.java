package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.blocks.AshBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EPBlocks {

    //Registry base blocks.
    public static final RegistryObject<Block> SCARLET_DIRT = normal("scarlet_dirt", Blocks.DIRT);
    public static final RegistryObject<Block> SCARLET_STONE = special("scarlet_stone", BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 6.0F));
    public static final RegistryObject<Block> HARD_SLATE = special("hard_slate", BlockBehaviour.Properties.of(Material.STONE).strength(3.5F, 8.0F));
    //Registry some block with special functions.
    public static final RegistryObject<AshBlock> ASH_BLOCK = EPBase.registerBlock("ash_block",
            () -> new AshBlock(BlockBehaviour.Properties.of(Material.SNOW).strength(20.0F, 3.0F).sound(SoundType.SAND)), EPBase.EP_TAB);
    //Registry ore blocks.
    public static final RegistryObject<Block> SCARLET_COAL_ORE = normal("scarlet_coal_ore", Blocks.COAL_ORE);
    public static final RegistryObject<Block> SCARLET_COPPER_ORE = normal("scarlet_copper_ore", Blocks.COPPER_ORE);
    public static final RegistryObject<Block> SCARLET_IRON_ORE = normal("scarlet_iron_ore", Blocks.IRON_ORE);
    public static final RegistryObject<Block> SCARLET_GOLD_ORE = normal("scarlet_gold_ore", Blocks.GOLD_ORE);
    public static final RegistryObject<Block> SCARLET_SILVER_ORE = normal("scarlet_silver_ore", Blocks.IRON_ORE);
    public static final RegistryObject<Block> SCARLET_DIAMOND_ORE = normal("scarlet_diamond_ore", Blocks.DIAMOND_ORE);
    //Registry the block of some ore materials.
    public static final RegistryObject<Block> SCARLET_COAL_BLOCK = normal("scarlet_coal_block", Blocks.COAL_ORE);
    public static final RegistryObject<Block> SCARLET_COPPER_BLOCK = normal("scarlet_copper_block", Blocks.COPPER_ORE);
    public static final RegistryObject<Block> SCARLET_IRON_BLOCK = normal("scarlet_iron_block", Blocks.IRON_ORE);
    public static final RegistryObject<Block> SCARLET_GOLD_BLOCK = normal("scarlet_gold_block", Blocks.GOLD_ORE);
    public static final RegistryObject<Block> SCARLET_SILVER_BLOCK = normal("scarlet_silver_block", Blocks.IRON_ORE);
    public static final RegistryObject<Block> SCARLET_DIAMOND_BLOCK = normal("scarlet_diamond_block", Blocks.DIAMOND_ORE);
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = normal("raw_silver_block", Blocks.RAW_IRON_BLOCK);
    public static final RegistryObject<Block> SILVER_BLOCK = normal("silver_block", Blocks.IRON_BLOCK);
    public static final RegistryObject<Block> EMBERIUM_BLOCK = normal("emberium_block", Blocks.DIAMOND_BLOCK);
    //Registry the normal block but with cutting.
    public static final RegistryObject<Block> CHISELED_SCARLET_STONE = normal("chiseled_scarlet_stone", Blocks.STONE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE = normal("polished_scarlet_stone", Blocks.STONE);
    public static final RegistryObject<Block> CHISELED_HARD_SLATE = normal("chiseled_scarlet_stone", Blocks.STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE = normal("polished_scarlet_stone", Blocks.STONE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS = normal("hardslate_bricks", Blocks.STONE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_BRICKS = normal("cracked_hardslate_bricks", Blocks.STONE);
    public static final RegistryObject<Block> HARD_SLATE_TILES = normal("hardslate_tiles", Blocks.STONE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_TILES = normal("cracked_hardslate_tiles", Blocks.STONE);
    //Registry the slab blocks.
    public static final RegistryObject<Block> SCARLET_STONE_SLAB = slab("scarlet_stone_slab", Blocks.STONE);
    //Registry the stair blocks.
    public static final RegistryObject<Block> SCARLET_STONE_STAIR = stair("scarlet_stone_stair", SCARLET_STONE, Blocks.STONE);

    private static RegistryObject<Block> normal(String name, BlockBehaviour behaviour) {
        return EPBase.registerBlock(name, () -> new Block(BlockBehaviour.Properties.copy(behaviour)), EPBase.EP_TAB);
    }

    private static RegistryObject<Block> special(String name, BlockBehaviour.Properties properties) {
        return EPBase.registerBlock(name, () -> new Block(properties), EPBase.EP_TAB);
    }

    private static RegistryObject<Block> slab(String name, BlockBehaviour behaviour) {
        return EPBase.registerBlock(name, () -> new SlabBlock(BlockBehaviour.Properties.copy(behaviour)), EPBase.EP_TAB);
    }

    private static <T extends Block> RegistryObject<Block> stair(String name, Supplier<T> block, BlockBehaviour behaviour) {
        return EPBase.registerBlock(name, () -> new StairBlock(() -> block.get().defaultBlockState(), BlockBehaviour.Properties.copy(behaviour)), EPBase.EP_TAB);
    }

    public static void register(IEventBus eventBus) {
        EPBase.BLOCKS.register(eventBus);
    }

}
