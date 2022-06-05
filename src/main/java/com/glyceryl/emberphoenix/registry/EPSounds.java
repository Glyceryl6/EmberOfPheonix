package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.EmberOfPhoenix;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class EPSounds {

    private static final RegistryObject<SoundEvent> ASH_BLOCK_DIG = registerSoundEvents("ash_block_dig");
    private static final RegistryObject<SoundEvent> EMBERIUM_DIG = registerSoundEvents("emberium_dig");
    private static final RegistryObject<SoundEvent> ASH_BLOCK_STEP = registerSoundEvents("ash_block_step");
    private static final RegistryObject<SoundEvent> EMBERIUM_STEP = registerSoundEvents("emberium_step");
    public static final RegistryObject<SoundEvent> EQUIP_BLAZE = registerSoundEvents("equip_blaze");
    public static final RegistryObject<SoundEvent> EQUIP_EMBERIUM = registerSoundEvents("equip_emberium");

    public static final ForgeSoundType ASH_BLOCK = new ForgeSoundType(1.0F, 1.0F, ASH_BLOCK_DIG, ASH_BLOCK_STEP, ASH_BLOCK_DIG, ASH_BLOCK_STEP, ASH_BLOCK_STEP);
    public static final ForgeSoundType EMBERIUM_BLOCK = new ForgeSoundType(1.0F, 1.0F, EMBERIUM_DIG, EMBERIUM_STEP, EMBERIUM_DIG, EMBERIUM_STEP, EMBERIUM_STEP);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return RegistryBase.SOUNDS.register(name, () -> new SoundEvent(EmberOfPhoenix.prefix(name)));
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.SOUNDS.register(eventBus);
    }

}