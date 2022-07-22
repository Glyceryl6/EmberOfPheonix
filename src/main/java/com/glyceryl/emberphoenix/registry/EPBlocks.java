package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.blocks.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
    private static final BlockBehaviour.Properties PLANT_PROPERTIES = BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().sound(SoundType.GRASS);

    //注册一般的方块
    public static final RegistryObject<Block> HARD_SLATE = normal("hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> SCARLET_STONE = normal("scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> TUMBLEWEED_PLANKS = normal("tumbleweed_planks", BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    //注册一些有特定功能的方块
    public static final RegistryObject<Block> SCARLET_DIRT = RegistryBase.registerBlock("scarlet_dirt",
            () -> new ScarletDirt(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<AshBlock> ASH_BLOCK = RegistryBase.registerBlock("ash_block",
            () -> new AshBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(20.0F, 3.0F)
                    .sound(EPSounds.ASH_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<EternalFireAltar> ETERNAL_FIRE_ALTAR = RegistryBase.registerBlock("eternal_fire_altar",
            () -> new EternalFireAltar(BlockBehaviour.Properties.of(Material.STONE).strength(5.0F, 6.0F)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<WildfireFigure> WILDFIRE_FIGURE = RegistryBase.registerBlock("wildfire_figure",
            () -> new WildfireFigure(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 3.0F).sound(SoundType.STONE)));
    public static final RegistryObject<EternalFire> ETERNAL_FIRE = RegistryBase.BLOCKS.register("eternal_fire",
            () -> new EternalFire(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_LIGHT_GREEN)
                    .strength(-1.0F, 3600000.0F).noDrops().noCollission().lightLevel((state) -> 15).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> INVISIBLE_BARRIER = RegistryBase.BLOCKS.register("invisible_barrier",
            () -> new BarrierBlock(BlockBehaviour.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.0F)
                    .noDrops().noCollission().isValidSpawn(EPBlocks::never).sound(SoundType.STONE)));
    //注册植物类方块
    public static final RegistryObject<EPTallGrass> BARREN_GRASS = RegistryBase.registerBlock("barren_grass", () -> new EPTallGrass(PLANT_PROPERTIES.instabreak()));
    public static final RegistryObject<FireFlower> FIRE_FLOWER = RegistryBase.registerBlock("fire_flower", () -> new FireFlower(PLANT_PROPERTIES.instabreak()));
    public static final RegistryObject<Tumbleweed> TUMBLEWEED = RegistryBase.registerBlock("tumbleweed", () -> new Tumbleweed(PLANT_PROPERTIES.strength(0.3F)));
    public static final RegistryObject<EPDoublePlant> BARREN_TALL_GRASS = RegistryBase.registerBlock("barren_tall_grass", () -> new EPDoublePlant(PLANT_PROPERTIES.instabreak()));
    public static final RegistryObject<RedstoneBerryBush> REDSTONE_BERRY_BUSH = RegistryBase.BLOCKS.register("redstone_berry_bush",
            () -> new RedstoneBerryBush(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
    //注册矿石方块
    public static final RegistryObject<Block> SCARLET_COAL_ORE = ore("scarlet_coal_ore");
    public static final RegistryObject<Block> SCARLET_IRON_ORE = ore("scarlet_iron_ore");
    public static final RegistryObject<Block> SCARLET_GOLD_ORE = ore("scarlet_gold_ore");
    public static final RegistryObject<Block> SCARLET_COPPER_ORE = ore("scarlet_copper_ore");
    public static final RegistryObject<Block> SCARLET_SILVER_ORE = ore("scarlet_silver_ore");
    public static final RegistryObject<Block> SCARLET_DIAMOND_ORE = ore("scarlet_diamond_ore");
    //注册矿物对应金属的块
    public static final RegistryObject<Block> SILVER_BLOCK = normal("silver_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = normal("raw_silver_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final RegistryObject<Block> DENSE_GLOWSTONE = normal("dense_glowstone", BlockBehaviour.Properties.copy(Blocks.STONE).strength(8.0F, 8.0F));
    public static final RegistryObject<Block> EMBERIUM_BLOCK = fireProof("emberium_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(EPSounds.EMBERIUM_BLOCK));
    //注册经切石机加工后的方块
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_BRICKS = normal("cracked_hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CRACKED_HARD_SLATE_TILES = normal("cracked_hardslate_tiles", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> CHISELED_SCARLET_STONE = normal("chiseled_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE = normal("polished_scarlet_stone", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> CHISELED_HARD_SLATE = normal("chiseled_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE = normal("polished_hardslate", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS = normal("hardslate_bricks", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES = normal("hardslate_tiles", STRENGTH_HARD_SLATE);
    //注册墙方块
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_WALL = wall("hardslate_brick_wall");
    public static final RegistryObject<Block> HARD_SLATE_TILES_WALL = wall("hardslate_tile_wall");
    //注册台阶方块
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_SLAB = slab("polished_scarlet_stone_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_SLAB = slab("polished_hardslate_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_SLAB = slab("hardslate_brick_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_SLAB = slab("hardslate_tile_slab", STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> SCARLET_STONE_SLAB = slab("scarlet_stone_slab", STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE_SLAB = slab("hardslate_slab", STRENGTH_SCARLET_STONE);
    //注册楼梯方块
    public static final RegistryObject<Block> POLISHED_SCARLET_STONE_STAIR = stair("polished_scarlet_stone_stair", POLISHED_SCARLET_STONE, STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> POLISHED_HARD_SLATE_STAIR = stair("polished_hardslate_stair", POLISHED_HARD_SLATE, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_BRICKS_STAIR = stair("hardslate_brick_stair", HARD_SLATE_BRICKS, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> HARD_SLATE_TILES_STAIR = stair("hardslate_tile_stair", HARD_SLATE_TILES, STRENGTH_HARD_SLATE);
    public static final RegistryObject<Block> SCARLET_STONE_STAIR = stair("scarlet_stone_stair", SCARLET_STONE, STRENGTH_SCARLET_STONE);
    public static final RegistryObject<Block> HARD_SLATE_STAIR = stair("hardslate_stair", HARD_SLATE, STRENGTH_HARD_SLATE);
    //添加一系列的风滚草木制品
    public static final RegistryObject<Block> TUMBLEWEED_PRESSURE_PLATE = RegistryBase.registerBlock("tumbleweed_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<Block> TUMBLEWEED_FENCE_GATE = RegistryBase.registerBlock("tumbleweed_fence_gate", () -> new FenceGateBlock(copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<Block> TUMBLEWEED_TRAPDOOR = RegistryBase.registerBlock("tumbleweed_trapdoor", () -> new TrapDoorBlock(copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> TUMBLEWEED_BUTTON = RegistryBase.registerBlock("tumbleweed_button", () -> new WoodButtonBlock(copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<Block> TUMBLEWEED_STAIR = RegistryBase.registerBlock("tumbleweed_stairs", () -> new StairBlock(() -> TUMBLEWEED_PLANKS.get().defaultBlockState(), copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> TUMBLEWEED_FENCE = RegistryBase.registerBlock("tumbleweed_fence", () -> new FenceBlock(copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> TUMBLEWEED_DOOR = RegistryBase.registerBlock("tumbleweed_door", () -> new DoorBlock(copy(Blocks.OAK_DOOR)));
    public static final RegistryObject<Block> TUMBLEWEED_SLAB = RegistryBase.registerBlock("tumbleweed_slab", () -> new SlabBlock(copy(Blocks.OAK_SLAB)));

    public static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static BlockBehaviour.Properties copy(BlockBehaviour blockBehaviour) {
        return BlockBehaviour.Properties.copy(blockBehaviour);
    }

    public static RegistryObject<Block> fireProof(String name, BlockBehaviour.Properties properties) {
        return RegistryBase.registerFireProofBlock(name, () -> new Block(properties));
    }

    private static RegistryObject<Block> normal(String name, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new Block(properties));
    }

    private static RegistryObject<Block> ore(String name) {
        return RegistryBase.registerBlock(name, () -> new OreBlock(STRENGTH_EMBER_ORE));
    }

    private static RegistryObject<Block> wall(String name) {
        return RegistryBase.registerBlock(name, () -> new WallBlock(STRENGTH_HARD_SLATE));
    }

    private static RegistryObject<Block> slab(String name, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new SlabBlock(properties));
    }

    private static <T extends Block> RegistryObject<Block> stair(String name, Supplier<T> block, BlockBehaviour.Properties properties) {
        return RegistryBase.registerBlock(name, () -> new StairBlock(() -> block.get().defaultBlockState(), properties));
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.BLOCKS.register(eventBus);
    }

}