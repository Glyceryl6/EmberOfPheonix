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
    public float offset = 0.0F;
    public float rotateSpeed = 0.0F;

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
        double d = 0.1D;
        double y = 0.05D;
        this.offset += 0.01F;
        this.rotateSpeed += this.offset;
        if (this.isActivated()) {
            if (this.delay <= 100) {
                Block fire = EPBlocks.ETERNAL_FIRE.get();
                BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
                AABB aabb = (new AABB(centerPos)).inflate(2);
                int playerCount = level.getEntitiesOfClass(Player.class, aabb).size();
                //召唤时，将以自身为中心 7×5×7 范围内的所有空气方块替换为无碰撞箱的屏障方块，以防止召唤仪式被破坏
                for (BlockPos blockPos : BlockPos.withinManhattan(centerPos, r, 2, r)) {
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
                    for(int i = 0; i < 10; ++i) {
                        double dx = this.getRandomX(0.5D);
                        double dy = this.getRandomY() - 0.25D;
                        double dz = this.getRandomZ(0.5D);
                        double d0 = (this.random.nextDouble() - 0.5D) * 2.0D;
                        double d1 = -this.random.nextDouble();
                        double d2 = (this.random.nextDouble() - 0.5D) * 2.0D;
                        this.level.addParticle(ParticleTypes.ENCHANT, dx, dy, dz, d0, d1, d2);
                    }
                    ParticleOptions particle = ParticleTypes.FLAME;
                    //中间的粒子发射点
                    this.level.addParticle(particle, (float)pos1.getX() + 0.5F, pos1.getY(), (float)pos1.getZ() + 0.5F, -d, y, d);
                    this.level.addParticle(particle, (float)pos2.getX() + 0.5F, pos2.getY(), (float)pos2.getZ() + 0.5F, -d, y, -d);
                    this.level.addParticle(particle, (float)pos3.getX() + 0.5F, pos3.getY(), (float)pos3.getZ() + 0.5F, d, y, d);
                    this.level.addParticle(particle, (float)pos4.getX() + 0.5F, pos4.getY(), (float)pos4.getZ() + 0.5F, d, y, -d);
                    //向东南方向偏移
                    this.level.addParticle(particle, (float)pos1.getX() + 1.0F, pos1.getY(), (float)pos1.getZ() + 1.0F, -0.2D, y, 0.1D); //B4
                    this.level.addParticle(particle, (float)pos2.getX() + 1.0F, pos2.getY(), (float)pos2.getZ() + 1.0F, -0.1D, 0.03D, -0.1D); //D4
                    this.level.addParticle(particle, (float)pos3.getX() + 1.0F, pos3.getY(), (float)pos3.getZ() + 1.0F, 0.1D, y, 0.1D); //A4
                    this.level.addParticle(particle, (float)pos4.getX() + 1.0F, pos4.getY(), (float)pos4.getZ() + 1.0F, 0.1D, y, -0.2D); //C4
                    //向东北方向偏移
                    this.level.addParticle(particle, (float)pos1.getX() + 1.0F, pos1.getY(), (float)pos1.getZ(), -0.1D, 0.03D, 0.1D); //B2
                    this.level.addParticle(particle, (float)pos2.getX() + 1.0F, pos2.getY(), (float)pos2.getZ(), -0.2D, y, -0.1D); //D2
                    this.level.addParticle(particle, (float)pos3.getX() + 1.0F, pos3.getY(), (float)pos3.getZ(), 0.1D, y, 0.2D); //A2
                    this.level.addParticle(particle, (float)pos4.getX() + 1.0F, pos4.getY(), (float)pos4.getZ(), 0.1D, y, -0.1D); //C2
                    //向西南方向偏移
                    this.level.addParticle(particle, (float)pos1.getX(), pos1.getY(), (float)pos1.getZ() + 1.0F, -0.1D, y, 0.1D); //B3
                    this.level.addParticle(particle, (float)pos2.getX(), pos2.getY(), (float)pos2.getZ() + 1.0F, -0.1D, y, -0.2D); //D3
                    this.level.addParticle(particle, (float)pos3.getX(), pos3.getY(), (float)pos3.getZ() + 1.0F, 0.2D, y, 0.1D); //A3
                    this.level.addParticle(particle, (float)pos4.getX(), pos4.getY(), (float)pos4.getZ() + 1.0F, 0.1D, 0.03D, -0.1D); //C3
                    //向西北方向偏移
                    this.level.addParticle(particle, (float)pos1.getX(), pos1.getY(), (float)pos1.getZ(), -0.1D, y, 0.2D); //B1
                    this.level.addParticle(particle, (float)pos2.getX(), pos2.getY(), (float)pos2.getZ(), -0.1D, y, -0.1D); //D1
                    this.level.addParticle(particle, (float)pos3.getX(), pos3.getY(), (float)pos3.getZ(), 0.1D, 0.03D, 0.1D); //A1
                    this.level.addParticle(particle, (float)pos4.getX(), pos4.getY(), (float)pos4.getZ(), 0.2D, y, -0.1D); //C1
                }
            } else {
                if (!this.level.isClientSide) {
                    BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
                    AABB aabb = (new AABB(centerPos)).inflate(2);
                    int gatewayCount = level.getEntitiesOfClass(PhoenixGateway.class, aabb).size();
                    //召唤完毕之后，移除之前生成的所有屏障方块
                    for (BlockPos blockPos : BlockPos.withinManhattan(centerPos, r, 2, r)) {
                        if (this.level.getBlockState(blockPos).is(EPBlocks.INVISIBLE_BARRIER.get())) {
                            this.level.removeBlock(blockPos, false);
                        }
                    }
                    this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
                    this.level.removeBlock(this.getOnPos().relative(Direction.DOWN), false);
                    //如果该区域内不存在传送门，则创建一个新的传送门
                    if (gatewayCount == 0) {
                        PhoenixGateway gateway = new PhoenixGateway(EPEntity.PHOENIX_GATEWAY.get(), level);
                        gateway.setPos(Vec3.atCenterOf(this.getOnPos()));
                        this.level.addFreshEntity(gateway);
                    }
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