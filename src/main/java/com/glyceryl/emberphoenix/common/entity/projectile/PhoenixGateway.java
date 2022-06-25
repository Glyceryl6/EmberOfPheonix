package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPDimensions;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

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
            for(int i = 0; i < 5; ++i) {
                double dx = this.getRandomX(0.5D);
                double dy = this.getRandomY() - 0.25D;
                double dz = this.getRandomZ(0.5D);
                double d0 = (this.random.nextDouble() - 0.5D) * 2.0D;
                double d1 = -this.random.nextDouble();
                double d2 = (this.random.nextDouble() - 0.5D) * 2.0D;
                this.level.addParticle(ParticleTypes.PORTAL, dx, dy, dz, d0, d1, d2);
            }
        }
        if (!this.level.isClientSide) {
            for (Entity entity : this.level.getEntities(this, getBoundingBox())) {
                if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
                    if (this.level.getServer().getLevel(EPDimensions.PHOENIX_KEY) == this.level) {
                        this.teleport(entity, entity.getServer().getLevel(Level.OVERWORLD), entity.getOnPos(), true);
                    } else if (this.level.getServer().getLevel(Level.OVERWORLD) == this.level) {
                        this.teleport(entity, entity.getServer().getLevel(EPDimensions.PHOENIX_KEY), entity.getOnPos(), true);
                    }
                }
            }
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

    public void teleport(Entity entity, ServerLevel destination, BlockPos pos, boolean findTop) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                int y = pos.getY();
                if (findTop) {
                    y = destination.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
                }
                entity.teleportTo(pos.getX(), y, pos.getZ());
                return entity;
            }
        });
    }

}