package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PhoenixGateway extends Entity {

    public float size = 1.0F;
    private float count = 0.01F;

    public PhoenixGateway(EntityType<?> type, Level level) {
        super(type, level);
    }

    public PhoenixGateway(Level level, double x, double y, double z) {
        this(EPEntity.PHOENIX_GATEWAY.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            this.count += 0.01F;
            if (this.count >= 0.5F) {
                this.count = 0.5F;
            }
            this.size = 4 * Mth.abs(Mth.sin(Mth.PI * count));
        }
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
