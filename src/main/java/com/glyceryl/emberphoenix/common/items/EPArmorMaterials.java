package com.glyceryl.emberphoenix.common.items;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.registry.EPItems;
import com.glyceryl.emberphoenix.registry.EPSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum EPArmorMaterials implements ArmorMaterial {

    EMBERIUM("emberium", new int[]{429, 495, 528, 363}, new int[]{3, 6, 7, 3}, 10, EPSounds.EQUIP_EMBERIUM,
            2.0F, 0.0F, () -> Ingredient.of(EPItems.EMBERIUM_INGOT.get())),

    BLAZE("blaze", new int[]{273, 315, 336, 231}, new int[]{4, 8, 10, 4}, 10, EPSounds.EQUIP_BLAZE,
            3.0F, 0.05F, () -> Ingredient.of(EPItems.BLAZE_RUBY.get()));

    private final String name;
    private final int[] slotDurability;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    EPArmorMaterials(String name, int[] slotDurability, int[] slotProtections, int enchantmentValue, SoundEvent soundEvent,
                     float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
        this.repairIngredient = new LazyLoadedValue<>(supplier);
        this.knockbackResistance = knockbackResistance;
        this.enchantmentValue = enchantmentValue;
        this.slotProtections = slotProtections;
        this.slotDurability = slotDurability;
        this.toughness = toughness;
        this.sound = soundEvent;
        this.name = name;
    }

    public int getDurabilityForSlot(EquipmentSlot slot) {
        return this.slotDurability[slot.getIndex()];
    }

    public int getDefenseForSlot(EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return EmberOfPhoenix.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}