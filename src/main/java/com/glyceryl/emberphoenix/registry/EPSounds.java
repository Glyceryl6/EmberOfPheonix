package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.RegistryBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@SuppressWarnings("deprecation")
public class EPSounds {

    private static final SoundEvent ASH_BLOCK_DIG = createSoundEvent("ash_block_dig");
    private static final SoundEvent EMBERIUM_DIG = createSoundEvent("emberium_dig");
    private static final SoundEvent ASH_BLOCK_STEP = createSoundEvent("ash_block_step");
    private static final SoundEvent EMBERIUM_STEP = createSoundEvent("emberium_step");

    public static final SoundEvent EQUIP_BLAZE = createSoundEvent("equip_blaze");
    public static final SoundEvent EQUIP_EMBERIUM = createSoundEvent("equip_emberium");

    public static final SoundType ASH_BLOCK = new SoundType(1.0F, 1.0F, ASH_BLOCK_DIG, ASH_BLOCK_STEP, ASH_BLOCK_DIG, ASH_BLOCK_STEP, ASH_BLOCK_STEP);
    public static final SoundType EMBERIUM_BLOCK = new SoundType(1.0F, 1.0F, EMBERIUM_DIG, EMBERIUM_STEP, EMBERIUM_DIG, EMBERIUM_STEP, EMBERIUM_STEP);

    private static SoundEvent createSoundEvent(String sound) {
        ResourceLocation name = EmberOfPhoenix.prefix(sound);
        return new SoundEvent(name).setRegistryName(name);
    }

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(EQUIP_BLAZE, EQUIP_EMBERIUM);
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.SOUNDS.register(eventBus);
    }

}