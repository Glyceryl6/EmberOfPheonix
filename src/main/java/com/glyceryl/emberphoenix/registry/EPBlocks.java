package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.blocks.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class EPBlocks {
    
    private static final BlockBehaviour.Properties STRENGTH_SCARLET_STONE = BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.0F, 6.0F).sound(SoundType.NETHERRACK);
    private static final BlockBehaviour.Properties STRENGTH_HARD_SLATE = BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.5F, 8.0F).sound(SoundType.DEEPSLATE);
    private static final BlockBehaviour.Properties STRENGTH_EMBER_ORE = BlockBehaviour.Properties.copy(Blocks.STONE).strength(3.5F, 8.0F).sound(SoundType.NETHER_ORE);
    private static final BlockBehaviour.Properties PLANT_PROPERTIES = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS);

    //Registry base blocks.
    public static final RegistryObject<Block> SCARLET_STONE = normal("scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE = normal("hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> SCARLET_DIRT = normal("scarlet_dirt", BlockBehaviour.Properties.copy(Blocks.DIRT));
    //Registry new block with special functions.
    public static final RegistryObject<AshBlock> ASH_BLOCK = RegistryBase.registerBlock("ash_block",
            () -> new AshBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(20.0F, 3.0F)
                    .sound(EPSounds.ASH_BLOCK).requiresCorrectToolForDrops()), RegistryBase.EP_TAB);
    public static final RegistryObject<EternalFireAltar> ETERNAL_FIRE_ALTAR = RegistryBase.registerBlock("eternal_fire_altar",
            () -> new EternalFireAltar(BlockBehaviour.Properties.of(Material.STONE).strength(5.0F, 6.0F)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()), RegistryBase.EP_TAB);
    public static final RegistryObject<EternalFire> ETERNAL_FIRE = RegistryBase.BLOCKS.register("eternal_fire",
            () -> new EternalFire(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN)
                    .noDrops().noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOL)));
    //Registry plants and flowers.
    public static final RegistryObject<EPTallGrass> BARREN_GRASS = RegistryBase.registerBlock("barren_grass",
            () -> new EPTallGrass(PLANT_PROPERTIES), RegistryBase.EP_TAB);
    public static final RegistryObject<EPDoublePlant> BARREN_TALL_GRASS = RegistryBase.registerBlock("barren_tall_grass",
            () -> new EPDoublePlant(PLANT_PROPERTIES), RegistryBase.EP_TAB);
    //Registry ore blocks.
    public static final RegistryObject<Block> SCARLET_COAL_ORE = ore("scarlet_coal_ore");
    public static final RegistryObject<Block> SCARLET_COPPER_ORE = ore("scarlet_copper_ore");
    public static final RegistryObject<Block> SCARLET_IRON_ORE = ore("scarlet_iron_ore");
    public static final RegistryObject<Block> SCARLET_GOLD_ORE = ore("scarlet_gold_ore");
    public static final RegistryObject<Block> SCARLET_SILVER_ORE = ore("scarlet_silver_ore");
    public static final RegistryObject<Block> SCARLET_DIAMOND_ORE = ore("scarlet_diamond_ore");
    //Registry block of ore materials.
    public static final RegistryObject<Block> SILVER_BLOCK = normal("silver_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = normal("raw_silver_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final RegistryObject<Block> EMBERIUM_BLOCK = normal("emberium_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(EPSounds.EMBERIUM_BLOCK));
    public static final RegistryObject<Block> DENSE_GLOWSTONE = normal("dense_glowstone", BlockBehaviour.Properties.copy(Blocks.STONE).strength(8.0F, 8.0F));
    //Registry normal block with cutting.
    public static final RegistryObject<Block> CHISELED_SCARLET_STONE = normal("chiseled_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE = normal("polished_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> CHISELED_HARD_SLATE = normal("chiseled_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE = normal("polished_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS = normal("hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES = normal("hardslate_tiles", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_BRICKS = normal("cracked_hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_TILES = normal("cracked_hardslate_tiles", STRENGTH_HARD_SLATE);
    //Registry wall block.
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_WALL = wall("hardslate_brick_wall");
    public static final RegistryObject<Block> HARD_SLATE_TILES_WALL = wall("hardslate_tile_wall");
    //Registry slab block.
    public static final RegistryObject<Block> SCARLET_STONE_SLAB = slab("scarlet_stone_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE_SLAB = slab("hardslate_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_SLAB = slab("polished_scarlet_stone_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_SLAB = slab("polished_hardslate_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_SLAB = slab("hardslate_brick_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_SLAB = slab("hardslate_tile_slab", STRENGTH_HARD_SLATE);
    //Registry stair block.
    public static final RegistryObject<Block> SCARLET_STONE_STAIR = stair("scarlet_stone_stair", SCARLET_STONE, STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE_STAIR = stair("hardslate_stair", HARD_SLATE, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_STAIR = stair("polished_scarlet_stone_stair", POLISHED_SCARLET_STONE, STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_STAIR = stair("polished_hardslate_stair", POLISHED_HARD_SLATE, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_STAIR = stair("hardslate_brick_stair", HARD_SLATE_BRICKS, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_STAIR = stair("hardslate_tile_stair", HARD_SLATE_TILES, STRENGTH_HARD_SLATE);

    private static RegistryObject<Block> normal(String name, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new Block(properties), RegistryBase.EP_TAB);
    }

    private static RegistryObject<Block> ore(String name) {
        return RegistryBase.registerBlock(name, () -> new OreBlock(STRENGTH_EMBER_ORE), RegistryBase.EP_TAB);
    }

    private static RegistryObject<Block> wall(String name) {
        return RegistryBase.registerBlock(name, () -> new WallBlock(STRENGTH_HARD_SLATE), RegistryBase.EP_TAB);
    }

    private static RegistryObject<Block> slab(String name, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new SlabBlock(properties), RegistryBase.EP_TAB);
    }

    private static <T extends Block> RegistryObject<Block> stair(String name, Supplier<T> block, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new StairBlock(() -> block.get().defaultBlockState(), properties), RegistryBase.EP_TAB);
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.BLOCKS.register(eventBus);
    }

}