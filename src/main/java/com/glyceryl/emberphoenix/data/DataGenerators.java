package com.glyceryl.emberphoenix.data;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        ExistingFileHelper helper = evt.getExistingFileHelper();
        BlockTagsProvider blockTags = new EPBlockTags(generator, helper);
        generator.addProvider(blockTags);
    }

}
