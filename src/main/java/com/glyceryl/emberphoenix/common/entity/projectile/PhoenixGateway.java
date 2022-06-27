package com.glyceryl.emberphoenix.common.entity.projectile;

import com.glyceryl.emberphoenix.registry.EPBlocks;
import com.glyceryl.emberphoenix.registry.EPDimensions;
import com.glyceryl.emberphoenix.registry.EPEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.ArrayList;
import java.util.List;
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

        Block altar = EPBlocks.ETERNAL_FIRE_ALTAR.get();
        BlockPos centerPos = new BlockPos(Vec3.atCenterOf(this.getOnPos()));
        BlockPos pos1 = centerPos.relative(Direction.EAST, 2).relative(Direction.NORTH, 2).below();
        BlockPos pos2 = centerPos.relative(Direction.EAST, 2).relative(Direction.SOUTH, 2).below();
        BlockPos pos3 = centerPos.relative(Direction.WEST, 2).relative(Direction.NORTH, 2).below();
        BlockPos pos4 = centerPos.relative(Direction.WEST, 2).relative(Direction.SOUTH, 2).below();
        boolean flag1 = this.level.getBlockState(pos1).is(altar);
        boolean flag2 = this.level.getBlockState(pos2).is(altar);
        boolean flag3 = this.level.getBlockState(pos3).is(altar);
        boolean flag4 = this.level.getBlockState(pos4).is(altar);
        boolean hasEternalFireAltar = flag1 && flag2 && flag3 && flag4;
        if (!hasEternalFireAltar) {
            if (this.level.isClientSide) {
                float pitch = (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F;
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, pitch, false);
                this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
            }
            this.discard();
        }


        if (!this.level.isClientSide) {
            for (Entity entity : this.level.getEntities(this, getBoundingBox())) {
                if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
                    if (this.level.getServer().getLevel(EPDimensions.PHOENIX_KEY) == this.level) {
                        this.teleport(entity, entity.getServer().getLevel(Level.OVERWORLD), entity.getOnPos(), true);
                    } else if (this.level.getServer().getLevel(Level.OVERWORLD) == this.level) {
                        this.teleport(entity, entity.getServer().getLevel(EPDimensions.PHOENIX_KEY), entity.getOnPos(), false);
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
                List<BlockPos> validPosList = new ArrayList<>(List.of());
                //给传送的目的地寻找一个合适且最近的落脚点，如果当前实体所在的位置符合要求，则自动跳过寻找
                for (BlockPos blockPos : BlockPos.withinManhattan(entity.getOnPos(), 256, destWorld.getLogicalHeight() / 2, 256)) {
                    if (destWorld.getBlockState(blockPos).getMaterial().isSolid() && !destWorld.getBlockState(blockPos).is(BlockTags.LEAVES)
                            && destWorld.isEmptyBlock(blockPos.above()) && destWorld.isEmptyBlock(blockPos.above(2))) {
                        entity.teleportTo(blockPos.getX(), blockPos.above().getY(), blockPos.getZ());
                        validPosList.add(blockPos);
                        entity.clearFire(); break;
                    }
                }

                //如果在半径256格内确实无法找到合适的落脚点，则会在玩家脚下生成一个固体方块或者给予玩家缓降效果
                if (validPosList.size() == 0) {
                    if (destWorld.getBlockState(entity.getOnPos()).getMaterial().isLiquid()) {
                        if (destWorld.getServer().getLevel(Level.OVERWORLD) == destWorld) {
                            destWorld.setBlock(entity.getOnPos(), Blocks.STONE.defaultBlockState(), 2);
                        } else if (destWorld.getServer().getLevel(EPDimensions.PHOENIX_KEY) == destWorld) {
                            destWorld.setBlock(entity.getOnPos(), EPBlocks.SCARLET_DIRT.get().defaultBlockState(), 2);
                        }
                    } else if (destWorld.isEmptyBlock(entity.getOnPos()) && entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 1, false, true));
                        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1, false, true));
                    }
                }
                validPosList.clear();
                return entity;
            }

        });
    }

}