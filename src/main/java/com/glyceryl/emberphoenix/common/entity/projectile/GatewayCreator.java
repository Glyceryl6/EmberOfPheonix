package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public class GatewayCreator extends Entity {

    private int delay = 0;

    public static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(GatewayCreator.class, EntityDataSerializers.BOOLEAN);

    public GatewayCreator(EntityType<?> type, Level level) {
        super(type, level);
    }

    public GatewayCreator(Level level, double x, double y, double z) {
        this(EPEntity.GATEWAY_CREATOR.get(), level);
        this.setPos(x, y, z);
    }

    public void setActivated(boolean flag) {
        this.entityData.set(ACTIVATED, flag);
    }

    public boolean isActivated() {
        return this.entityData.get(ACTIVATED);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ACTIVATED, Boolean.FALSE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.setActivated(compound.getBoolean("Activated"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("Activated", this.isActivated());
    }

    @Override
    public void tick() {
        super.tick();
        ++this.delay;
        int r = 3;
        double d = 0.15D;
        if (this.isActivated()) {
            if (this.delay <= 100) {
                Block fire = EPBlocks.ETERNAL_FIRE.get();
                BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
                AABB aabb = (new AABB(centerPos)).inflate(2);
                int playerCount = level.getEntitiesOfClass(Player.class, aabb).size();
                for (BlockPos blockPos : BlockPos.withinManhattan(centerPos, r, r, r)) {
                    if (this.level.getBlockState(blockPos).isAir()) {
                        this.level.setBlock(blockPos, EPBlocks.INVISIBLE_BARRIER.get().defaultBlockState(), 2);
                    }
                }
                BlockPos pos1 = centerPos.relative(Direction.EAST, 2).relative(Direction.NORTH, 2);
                BlockPos pos2 = centerPos.relative(Direction.EAST, 2).relative(Direction.SOUTH, 2);
                BlockPos pos3 = centerPos.relative(Direction.WEST, 2).relative(Direction.NORTH, 2);
                BlockPos pos4 = centerPos.relative(Direction.WEST, 2).relative(Direction.SOUTH, 2);
                boolean flag1 = this.level.getBlockState(pos1).is(fire);
                boolean flag2 = this.level.getBlockState(pos2).is(fire);
                boolean flag3 = this.level.getBlockState(pos3).is(fire);
                boolean flag4 = this.level.getBlockState(pos4).is(fire);
                boolean hasEternalFire = flag1 && flag2 && flag3 && flag4;
                if (this.level.isClientSide && hasEternalFire && this.delay <= 70) {
                    ParticleOptions particle = ParticleTypes.SOUL_FIRE_FLAME;
                    this.level.addParticle(particle, (float)pos1.getX() + 0.5F, pos1.getY(), (float)pos1.getZ() + 0.5F, -d, 0.06D, d);
                    this.level.addParticle(particle, (float)pos2.getX() + 0.5F, pos2.getY(), (float)pos2.getZ() + 0.5F, -d, 0.06D, -d);
                    this.level.addParticle(particle, (float)pos3.getX() + 0.5F, pos3.getY(), (float)pos3.getZ() + 0.5F, d, 0.06D, d);
                    this.level.addParticle(particle, (float)pos4.getX() + 0.5F, pos4.getY(), (float)pos4.getZ() + 0.5F, d, 0.06D, -d);
                }
            } else {
                if (!this.level.isClientSide) {
                    BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
                    AABB aabb = (new AABB(centerPos)).inflate(2);
                    int gatewayCount = level.getEntitiesOfClass(PhoenixGateway.class, aabb).size();
                    for (BlockPos blockPos : BlockPos.withinManhattan(centerPos, r, r, r)) {
                        if (this.level.getBlockState(blockPos).is(EPBlocks.INVISIBLE_BARRIER.get())) {
                            this.level.removeBlock(blockPos, false);
                        }
                    }
                    PhoenixGateway gateway = new PhoenixGateway(EPEntity.PHOENIX_GATEWAY.get(), level);
                    gateway.setPos(Vec3.atCenterOf(this.getOnPos()));
                    this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
                    this.level.removeBlock(this.getOnPos().relative(Direction.DOWN), false);
                    //this.level.addFreshEntity(gateway);
                    this.discard();
                }
            }
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

}