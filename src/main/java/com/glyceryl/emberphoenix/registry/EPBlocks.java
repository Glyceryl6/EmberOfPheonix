package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.common.blocks.AshBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EPBlocks {
    
    private static final BlockBehaviour.Properties STRENGTH_SCARLET_STONE = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.NETHERRACK).requiresCorrectToolForDrops().strength(3.0F, 6.0F);
    private static final BlockBehaviour.Properties STRENGTH_HARD_SLATE = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5F, 8.0F);
    private static final BlockBehaviour.Properties STRENGTH_EMBER_ORE = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops().strength(3.5F, 8.0F);

    //Registry base blocks.
    public static final RegistryObject<Block> SCARLET_STONE = special("scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE = special("hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> SCARLET_DIRT = EPBase.registerBlock("scarlet_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)), EPBase.EP_TAB);
    //Registry new block with special functions.
    public static final RegistryObject<AshBlock> ASH_BLOCK = EPBase.registerBlock("ash_block",
            () -> new AshBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(20.0F, 3.0F)
                    .sound(EPSounds.ASH_BLOCK).requiresCorrectToolForDrops()), EPBase.EP_TAB);
    //Registry ore blocks.
    public static final RegistryObject<Block> SCARLET_COAL_ORE = special("scarlet_coal_ore", STRENGTH_EMBER_ORE);
    public static final RegistryObject<Block> SCARLET_COPPER_ORE = special("scarlet_copper_ore", STRENGTH_EMBER_ORE);
    public static final RegistryObject<Block> SCARLET_IRON_ORE = special("scarlet_iron_ore",STRENGTH_EMBER_ORE);
    public static final RegistryObject<Block> SCARLET_GOLD_ORE = special("scarlet_gold_ore",STRENGTH_EMBER_ORE);
    public static final RegistryObject<Block> SCARLET_SILVER_ORE = special("scarlet_silver_ore", STRENGTH_EMBER_ORE);
    public static final RegistryObject<Block> SCARLET_DIAMOND_ORE = special("scarlet_diamond_ore", STRENGTH_EMBER_ORE);
    //Registry block of ore materials.
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = special("raw_silver_block", BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F));
    public static final RegistryObject<Block> SILVER_BLOCK = special("silver_block", BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F));
    public static final RegistryObject<Block> EMBERIUM_BLOCK = special("emberium_block", BlockBehaviour.Properties.of(Material.METAL).sound(EPSounds.EMBERIUM_BLOCK).strength(5.0F, 6.0F));
    //Registry normal block but with cutting.
    public static final RegistryObject<Block> CHISELED_SCARLET_STONE = special("chiseled_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE = special("polished_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> CHISELED_HARD_SLATE = special("chiseled_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE = special("polished_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS = special("hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES = special("hardslate_tiles", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_BRICKS = special("cracked_hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_TILES = special("cracked_hardslate_tiles", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> DENSE_GLOWSTONE = special("dense_glowstone", BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(8.0F, 8.0F));
    //Registry the wall block.
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_WALL = wall("hardslate_brick_wall", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_WALL = wall("hardslate_tile_wall", STRENGTH_HARD_SLATE);
    //Registry the slab block.
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_SLAB = slab("polished_scarlet_stone_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_SLAB = slab("polished_hardslate_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_SLAB = slab("hardslate_brick_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_SLAB = slab("hardslate_tile_slab", STRENGTH_HARD_SLATE);
    //Registry the stair block.
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_STAIR = stair("polished_scarlet_stone_stair", SCARLET_STONE, STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_STAIR = stair("polished_hardslate_stair", POLISHED_HARD_SLATE, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_STAIR = stair("hardslate_brick_stair", HARD_SLATE_BRICKS, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_STAIR = stair("hardslate_tile_stair", HARD_SLATE_TILES, STRENGTH_HARD_SLATE);

    private static RegistryObject<Block> special(String name, BlockBehaviour.Properties properties) {
        return EPBase.registerBlock(name, () -> new Block(properties), EPBase.EP_TAB);
    }

    private static RegistryObject<Block> wall(String name, BlockBehaviour.Properties properties) {
        return EPBase.registerBlock(name, () -> new WallBlock(properties), EPBase.EP_TAB);
    }

    private static RegistryObject<Block> slab(String name, BlockBehaviour.Properties properties) {
        return EPBase.registerBlock(name, () -> new SlabBlock(properties), EPBase.EP_TAB);
    }

    private static <T extends Block> RegistryObject<Block> stair(String name, Supplier<T> block, BlockBehaviour.Properties properties) {
        return EPBase.registerBlock(name, () -> new StairBlock(() -> block.get().defaultBlockState(), properties), EPBase.EP_TAB);
    }

    public static void register(IEventBus eventBus) {
        EPBase.BLOCKS.register(eventBus);
    }

}