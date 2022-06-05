package com.glyceryl.emberphoenix.common.blocks;

import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AshBlock extends FallingBlock {

    public AshBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (entity instanceof Player) {
            ItemStack itemStack = ((Player) entity).getItemBySlot(EquipmentSlot.FEET);
            if (!itemStack.is(EPItems.EMBER_BOOTS.get())) {
                double i = !entity.isCrouching() ? 0.2D : 0.05D;
                entity.setDeltaMovement(vec3.x * i, vec3.y, vec3.z * i);
            }
        }
    }

}