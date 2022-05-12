package com.glyceryl.emberphoenix.data;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EPBlockTags extends BlockTagsProvider {

    public static final TagKey<Block> PHOENIX_ORE_REPLACING = BlockTags.create(EmberOfPhoenix.prefix("phoenix_ore_replacing"));

    public EPBlockTags(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, EmberOfPhoenix.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        tag(PHOENIX_ORE_REPLACING).add(EPBlocks.ASH_BLOCK.get(), EPBlocks.SCARLET_STONE.get());
    }

}