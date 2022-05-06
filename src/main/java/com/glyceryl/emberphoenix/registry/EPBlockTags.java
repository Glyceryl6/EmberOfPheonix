package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EPBlockTags extends BlockTagsProvider {

    public static final TagKey<Block> MINE_WITH_EMBER_AXE = BlockTags.create(EmberOfPhoenix.prefix("mineable/ember_axe"));
    public static final TagKey<Block> MINE_WITH_EMBER_HOE = BlockTags.create(EmberOfPhoenix.prefix("mineable/ember_hoe"));
    public static final TagKey<Block> MINE_WITH_EMBER_PICKAXE = BlockTags.create(EmberOfPhoenix.prefix("mineable/ember_pickaxe"));
    public static final TagKey<Block> MINE_WITH_EMBER_SHOVEL = BlockTags.create(EmberOfPhoenix.prefix("mineable/ember_shovel"));

    public EPBlockTags(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, EmberOfPhoenix.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        this.tag(MINE_WITH_EMBER_SHOVEL).addTags(BlockTags.MINEABLE_WITH_SHOVEL).add(EPBlocks.ASH_BLOCK.get());
    }
}
