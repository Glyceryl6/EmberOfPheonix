package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

@SuppressWarnings("unused")
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
                this.count = 0.49F;
            }
            this.size = 4 * Mth.abs(Mth.sin(Mth.PI * count));
        }
        /*
        this.checkInsideBlocks();
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox());
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
                boolean flag = !entity.isPassenger() && entity.canChangeDimensions();
                if (entity instanceof LivingEntity && entity.getBbWidth() < this.getBbWidth() && this.level instanceof ServerLevel && flag) {
                    ResourceKey<Level> resourceKey = this.level.dimension() == EPDimensions.PHOENIX_KEY ? Level.OVERWORLD : EPDimensions.PHOENIX_KEY;
                    ServerLevel serverLevel = ((ServerLevel)this.level).getServer().getLevel(resourceKey);
                    if (serverLevel == null) {
                        return;
                    }
                    entity.changeDimension(serverLevel);
                }
            }
        }
        */
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