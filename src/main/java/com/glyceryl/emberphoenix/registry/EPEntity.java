package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.entity.EPEntityNames;
import com.glyceryl.emberphoenix.common.entity.monster.WildfireEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = EmberOfPhoenix.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EPEntity {

    public static final RegistryObject<EntityType<WildfireEntity>> WILDFIRE = make(EPEntityNames.WILDFIRE, WildfireEntity::new, MobCategory.MONSTER, 1.6F, 4.0F, true, 0xf6b201, 0xfff87e);

    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary) {
        return make(id, factory, classification, width, height, false, primary, secondary);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof, primary, secondary);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireProof) {
        return build(id, builder, fireProof, 0, 0);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> RegistryObject<EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof, int primary, int secondary) {
        if(fireproof) builder.fireImmune();
        RegistryObject<EntityType<E>> ret = RegistryBase.ENTITY.register(id.getPath(), () -> builder.build(id.toString()));
        if(primary != 0 && secondary != 0) {
            RegistryBase.ITEMS.register(id.getPath() + "_spawn_egg", () -> new ForgeSpawnEggItem(() -> (EntityType<? extends Mob>) ret.get(), primary, secondary, EPItems.defaultBuilder()));
        }
        return ret;
    }

    private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
        return makeBuilder(factory, MobCategory.MISC, width, height, range, interval);
    }

    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval) {
        return EntityType.Builder.of(factory, classification).sized(width, height).setTrackingRange(range).setUpdateInterval(interval).setShouldReceiveVelocityUpdates(true);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        SpawnPlacements.register(WILDFIRE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(WILDFIRE.get(), WildfireEntity.setCustomAttributes().build());
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.ENTITY.register(eventBus);
    }

}