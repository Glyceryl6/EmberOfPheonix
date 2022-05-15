package com.glyceryl.emberphoenix.common.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class EPThrowable extends ThrowableProjectile implements IEPProjectile {

    public EPThrowable(EntityType<? extends EPThrowable> type, Level worldIn) {
        super(type, worldIn);
    }

    public EPThrowable(EntityType<? extends EPThrowable> type, Level worldIn, double x, double y, double z) {
        super(type, x, y, z, worldIn);
    }

    public EPThrowable(EntityType<? extends EPThrowable> type, Level worldIn, LivingEntity throwerIn) {
        super(type, throwerIn, worldIn);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
}
