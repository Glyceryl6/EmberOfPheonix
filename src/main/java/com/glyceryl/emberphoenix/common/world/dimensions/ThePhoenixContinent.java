package com.glyceryl.emberphoenix.common.world.dimensions;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID)
public class ThePhoenixContinent {

    @SubscribeEvent
    public static void registerFillerBlocks(FMLCommonSetupEvent event) {
        Set<Block> replaceableBlocks = new HashSet();
        replaceableBlocks.add(EPBlocks.SCARLET_STONE.get());
        replaceableBlocks.add(EPBlocks.SCARLET_DIRT.get());
        replaceableBlocks.add(Blocks.BASALT);
        event.enqueueWork(() -> {
            WorldCarver.CAVE.replaceableBlocks = (new ImmutableSet.Builder()).addAll(WorldCarver.CAVE.replaceableBlocks).addAll(replaceableBlocks).build();
            WorldCarver.CANYON.replaceableBlocks = (new ImmutableSet.Builder()).addAll(WorldCarver.CANYON.replaceableBlocks).addAll(replaceableBlocks).build();
        });
    }

    @SubscribeEvent
    public static void registerDimensionSpecialEffects(FMLClientSetupEvent event) {
        DimensionSpecialEffects customEffect = new DimensionSpecialEffects(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false) {
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return new Vec3(0.372549019608D, 0.372549019608D, 0.372549019608D);
            }

            public boolean isFoggyAt(int x, int y) {
                return false;
            }
        };
        event.enqueueWork(() -> DimensionSpecialEffects.EFFECTS.put(new ResourceLocation("emberphoenix:the_phoenix"), customEffect));
    }
}
