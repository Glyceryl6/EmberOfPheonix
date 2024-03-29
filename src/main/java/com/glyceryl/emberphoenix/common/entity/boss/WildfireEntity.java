package com.glyceryl.emberphoenix.common.entity.boss;

import com.glyceryl.emberphoenix.common.entity.ai.WildFireAttackGoal;
import com.glyceryl.emberphoenix.common.entity.monster.AncientBlaze;
import com.glyceryl.emberphoenix.common.entity.projectile.BoomerangFireball;
import com.glyceryl.emberphoenix.common.entity.projectile.FallingFireball;
import com.glyceryl.emberphoenix.common.entity.projectile.SmallCrack;
import com.glyceryl.emberphoenix.registry.EPEntity;
import com.glyceryl.emberphoenix.registry.EPParticles;
import com.glyceryl.emberphoenix.registry.EPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class WildfireEntity extends Monster implements PowerableMob {

    public static final Predicate<AncientBlaze> IS_POWER_SOURCE = (entity) -> !entity.isSpectator() && entity.isPowerSource();
    public static final EntityDataAccessor<Byte> ON_FIRE = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> ENCHANT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SHIELDING = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FIREBALL_COUNT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SMALL_CRACK_POWER = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FLAME_SHOWER_DENSITY = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);

    //给BOSS添加一个黄色的血条
    private final ServerBossEvent bossEvent =
            (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS))
            .setDarkenScreen(true).setCreateWorldFog(true);

    private float heightOffset = 0.5F;
    private int roarTime = 100;
    private int respireTime = 100;
    private int heightOffsetUpdateTime;
    public boolean shieldDisabled = false;

    public WildfireEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WildFireAttackGoal(this));
        this.goalSelector.addGoal(1, new WildfireEntity.SpawnMinionsGoal());
        this.goalSelector.addGoal(3, new WildfireEntity.SpawnFlameRainGoal());
        this.goalSelector.addGoal(5, new WildfireEntity.SpawnPowerSourceGoal());
        this.goalSelector.addGoal(4, new WildfireEntity.ShootSmallCrackGoal(this));
        this.goalSelector.addGoal(4, new WildfireEntity.ShootBoomerangFireball(this));
        this.goalSelector.addGoal(4, new WildfireEntity.FireballFusilladeGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WitherBoss.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return EPSounds.WILDFIRE_RESPIRE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return EPSounds.WILDFIRE_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return EPSounds.WILDFIRE_DEATH;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.8F;
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    public void teleportToSightOfEntity(@Nullable Entity entity) {
        Vec3 dest = findVecInLOSOf(entity);
        double srcX = getX();
        double srcY = getY();
        double srcZ = getZ();
        if (dest != null && entity != null) {
            teleportToNoChecks(dest.x, dest.y, dest.z);
            this.getLookControl().setLookAt(entity, 100F, 100F);
            this.yBodyRot = this.getYRot();
            if (!this.getSensing().hasLineOfSight(entity)) {
                teleportToNoChecks(srcX, srcY, srcZ);
            }
        }
    }

    //返回一个合适的坐标，如果没有则返回空值
    @Nullable
    public Vec3 findVecInLOSOf(@Nullable Entity targetEntity) {
        if (targetEntity == null) return null;
        double origX = getX();
        double origY = getY();
        double origZ = getZ();
        int tries = 100;
        for (int i = 0; i < tries; i++) {
            double range = random.nextDouble(12.0D) + 12.0D;
            double tx = targetEntity.getX() + random.nextGaussian() * range;
            double ty = targetEntity.getY() + random.nextGaussian() * 5.0D;
            double tz = targetEntity.getZ() + random.nextGaussian() * range;
            boolean destClear = randomTeleport(tx, ty, tz, true);
            boolean canSeeTargetAtDest = hasLineOfSight(targetEntity);
            this.teleportTo(origX, origY, origZ);
            if (destClear && canSeeTargetAtDest) {
                return new Vec3(tx, ty, tz);
            }
        }

        return null;
    }

    //如果该传送地点有效，则不用进行检测，直接进行传送
    private void teleportToNoChecks(double destX, double destY, double destZ) {
        SoundEvent sound = EPSounds.WILDFIRE_TELEPORT;
        double srcX = getX();
        double srcY = getY();
        double srcZ = getZ();
        this.teleportTo(destX, destY, destZ);
        this.level.playSound(null, srcX, srcY, srcZ, sound, this.getSoundSource(), 1.0F, 1.0F);
        this.playSound(sound, 1.0F, 1.0F);
        this.jumping = false;
    }

    //死亡时触发的事件
    private void triggerEvent() {
        BlockPos blockpos = this.getOnPos();
        AABB aabb = (new AABB(blockpos)).inflate(64.0D);
        List<Player> playerList = this.level.getEntitiesOfClass(Player.class, aabb);
        List<AncientBlaze> blazeList = this.level.getEntitiesOfClass(AncientBlaze.class, aabb);
        this.clearPlayerFire(playerList);
        this.killallBlazes(blazeList);
    }

    //灭掉周围玩家身上的火
    private void clearPlayerFire(List<Player> playerList) {
        for (Player player : playerList) {
            if (playerList.size() > 0) {
                player.clearFire();
            }
        }
    }

    //杀死周围所有的远古烈焰人
    private void killallBlazes(List<AncientBlaze> blazeList) {
        for (AncientBlaze blaze : blazeList) {
            if (blazeList.size() > 0) {
                blaze.setInvisible(true);
                blaze.kill();
                for (int i = 0; i < 30; ++i) {
                    double r0 = blaze.getRandomX(1.5D);
                    double r1 = blaze.getRandomY();
                    double r2 = blaze.getRandomZ(1.5D);
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    double d2 = this.random.nextGaussian() * 0.02D;
                    if (this.level.isClientSide) {
                        this.level.addParticle(ParticleTypes.POOF, r0, r1, r2, d0, d1, d2);
                    }
                }
            }
        }
    }

    //检测跟踪范围内是否存在远古烈焰人，有则回血
    private void checkBlazesForHeal() {
        BlockPos blockPos = this.getOnPos();
        AABB aabb = (new AABB(blockPos)).inflate(64.0D);
        List<AncientBlaze> blazeList = this.level.getEntitiesOfClass(AncientBlaze.class, aabb);
        int blazeCount = getBlazeAround(64.0D, IS_POWER_SOURCE).size();
        if (this.getHealth() < this.getMaxHealth() && blazeCount > 0) {
            this.bossEvent.setColor(BossEvent.BossBarColor.GREEN);
            this.entityData.set(ENCHANT, true);
            for (AncientBlaze blaze : blazeList) {
                if (blaze.isPowerSource()) {
                    this.makeParticlesTo(blaze);
                }
            }
            if (this.tickCount % 5 == 0) {
                this.heal(0.2F);
            }
        } else {
            this.bossEvent.setColor(BossEvent.BossBarColor.YELLOW);
            this.entityData.set(ENCHANT, false);
        }
    }

    //尝试用粒子特效将其与远古烈焰人连接起来
    private void makeParticlesTo(Entity entity) {
        if (this.level.isClientSide) {
            BlockPos blockPos = this.getOnPos().immutable();
            double sx = blockPos.getX() + 0.5D;
            double sy = blockPos.getY() + 2.0D;
            double sz = blockPos.getZ() + 0.5D;
            double dx = sx - entity.getX();
            double dy = sy - entity.getY() - entity.getEyeHeight();
            double dz = sz - entity.getZ();
            for (int i = 0; i < 5; i++) {
                level.addParticle(EPParticles.WILDFIRE_HEAL.get(), sx, sy, sz, -dx, -dy, -dz);
            }
        }
    }

    public boolean isFoil() {
        return this.entityData.get(ENCHANT);
    }

    @Override
    protected void tickDeath() {
        this.triggerEvent();
        super.tickDeath();
    }

    public void aiStep() {
        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.level.isClientSide) {

            if (!this.isSilent() && --this.respireTime < 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), EPSounds.WILDFIRE_RESPIRE, this.getSoundSource(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
                this.respireTime = 200 + this.random.nextInt(200);
            }

            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                float volume = 1.0F + this.random.nextFloat();
                float pitch = this.random.nextFloat() * 0.7F + 0.3F;
                this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(), volume, pitch, false);
            }

            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
        if (getShielding()) {
            this.level.addParticle(ParticleTypes.LAVA, getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
        if (this.entityData.get(ATTACKING)) {
            if (!this.isSilent() && --this.roarTime < 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), EPSounds.WILDFIRE_ROAR, this.getSoundSource(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
                this.roarTime = 200 + this.random.nextInt(200);
            }
            for (int i = 0; i < 16; i++) {
                this.level.addParticle(ParticleTypes.LAVA, getRandomX(0.75D), this.getRandomY(), this.getRandomZ(0.75D), 0.0D, 0.0D, 0.0D);
            }
        }
        this.checkBlazesForHeal();
        super.aiStep();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasFoil", this.isFoil());
        compound.putInt("Variant", this.entityData.get(VARIANT));
        compound.putInt("FireballCount", this.entityData.get(FIREBALL_COUNT));
        compound.putInt("SmallCrackPower", this.entityData.get(SMALL_CRACK_POWER));
        compound.putInt("FlameShowerDensity", this.entityData.get(FLAME_SHOWER_DENSITY));
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        this.entityData.set(ENCHANT, compound.getBoolean("hasFoil"));
        this.entityData.set(FIREBALL_COUNT, compound.getInt("FireballCount"));
        this.entityData.set(SMALL_CRACK_POWER, compound.getInt("SmallCrackPower"));
        this.entityData.set(FLAME_SHOWER_DENSITY, compound.getInt("FlameShowerDensity"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHIELDING, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(ENCHANT, false);
        this.entityData.define(ON_FIRE, (byte) 0);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(FIREBALL_COUNT, 17);
        this.entityData.define(SMALL_CRACK_POWER, 2);
        this.entityData.define(FLAME_SHOWER_DENSITY, 256);
    }

    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }

    public int getMaxSpawnClusterSize() {
        return (this.level.getDifficulty() == Difficulty.HARD) ? 2 : 1;
    }

    protected void customServerAiStep() {
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.random.nextGaussian() * (3 / ((this.getHealth() / 25) + 1));
        }

        LivingEntity livingentity = this.getTarget();
        if (livingentity != null && livingentity.getEyeY() > this.getEyeY() + (double) this.heightOffset && this.canAttack(livingentity)) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, ((double)0.3F - vec3.y) * (double)0.3F, 0.0D));
            this.hasImpulse = true;
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        super.customServerAiStep();
    }

    //发射出流星雨状的火球
    private void spawnFlameShower() {
        float pitch = 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F);
        this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, pitch);
        if (!this.level.isClientSide) {
            for (int i = 0; i < entityData.get(FLAME_SHOWER_DENSITY); i++) {
                float f = (float)(Math.random() * Math.PI * 2.0D);
                double x = (-((float)Math.sin(f)) * 0.75F) / 2.0D;
                double y = Math.abs(Math.random() * 0.75D) * 1.5D;
                double z = (-((float)Math.cos(f)) * 0.75F) / 2.0D;
                FallingFireball entity = new FallingFireball(this.getX(), this.getY(), this.getZ(), this.level);
                entity.setItem(Items.AIR.getDefaultInstance());
                entity.setDeltaMovement(x, y, z);
                entity.setSecondsOnFire(0);
                entity.setInvisible(true);
                this.level.addFreshEntity(entity);
            }
        }
    }

    //发射弹射物
    public void launchProjectile(Projectile projectile) {
        float bodyFacingAngle = ((this.yBodyRot * Mth.PI) / 180F);
        float pitch = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F;
        double ry = (this.getY() + this.getBbHeight() / 2.0F);
        double sx = getX() + (Mth.cos(bodyFacingAngle) * 0.65D);
        double sy = getY() + (this.getBbHeight() * 0.50D);
        double sz = getZ() + (Mth.sin(bodyFacingAngle) * 0.65D);
        double tx = Objects.requireNonNull(this.getTarget()).getX() - sx;
        double ty = (this.getTarget().getBoundingBox().minY + this.getTarget().getBbHeight() / 2.0F) - ry;
        double tz = this.getTarget().getZ() - sz;
        this.playSound(SoundEvents.BLAZE_SHOOT, 1.5F, pitch);
        projectile.moveTo(sx, sy, sz, getYRot(), getXRot());
        projectile.shoot(tx, ty, tz, 1.0F, 0.5F);
        projectile.setNoGravity(true);
        this.getLevel().addFreshEntity(projectile);
    }

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    public void checkDespawn() {
        boolean isPeaceful = level.getDifficulty() == Difficulty.PEACEFUL;
        if (isPeaceful && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    //获取一定范围内的远古烈焰人数量
    private List<AncientBlaze> getBlazeAround(double radius, Predicate<? super AncientBlaze> predicate) {
        BlockPos blockpos = this.getOnPos();
        AABB aabb = (new AABB(blockpos)).inflate(radius);
        return level.getEntitiesOfClass(AncientBlaze.class, aabb, predicate);
    }

    public void setShielding(boolean shielding) {
        if (!this.shieldDisabled) {
            this.entityData.set(SHIELDING, shielding);
        } else {
            this.entityData.set(SHIELDING, Boolean.FALSE);
        }
    }

    public boolean isPowered() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public boolean getShielding() {
        return (this.entityData.get(SHIELDING) && !this.shieldDisabled);
    }

    public void setAggressive(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return (this.entityData.get(ON_FIRE) & 1) != 0;
    }

    @Override
    public void setSharedFlagOnFire(boolean onFire) {
        byte b0 = this.entityData.get(ON_FIRE);
        if (onFire) {
            b0 = (byte)(b0 | 0x1);
        } else {
            b0 = (byte)(b0 & 0xFFFFFFFE);
        }
        this.entityData.set(ON_FIRE, b0);
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity entity) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.WITHER && super.canBeAffected(effect);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level.isClientSide) {
            if (isInvulnerableTo(source)) {
                playSound(SoundEvents.SHIELD_BLOCK, 0.3F, 0.5F);
                if (source.getDirectEntity() != null) {
                    int seconds = source.isProjectile() ? 12 : 8;
                    source.getDirectEntity().setSecondsOnFire(seconds);
                }
                return false;
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        boolean b = source instanceof EntityDamageSource && this.isFoil();
        if ((source == DamageSource.GENERIC || b || source.isExplosion()) && !source.isCreativePlayer())
            return isInvulnerable();
        return false;
    }

    //随机召唤3~6只远古烈焰人
    class SpawnMinionsGoal extends Goal {

        @Override
        public boolean canUse() {
            return getTarget() != null && distanceToSqr(getTarget()) <= 1024.0D && random.nextFloat() * 100.0F < 0.6F;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = getTarget();
            return livingentity != null && livingentity.isAlive() && canAttack(livingentity);
        }

        @Override
        public void tick() {
            LivingEntity livingentity = getTarget();
            int blazeCount = getBlazeAround(32.0D, EntitySelector.NO_SPECTATORS).size();
            int count = random.nextInt(4) + 3;
            boolean p = random.nextInt(10) < 6;
            if (blazeCount < 3 && p && livingentity != null && distanceTo(livingentity) < 12.0D) {
                double xx = livingentity.getX() - getX();
                double yy = livingentity.getY() - getY();
                double zz = livingentity.getZ() - getZ();
                for (int i = (int)Math.ceil((double)(getHealth() / getMaxHealth()) * (double) count); i > 0; --i) {
                    AncientBlaze blaze = EPEntity.ANCIENT_BLAZE.get().create(level);
                    if (blaze != null) {
                        double x = getX() + (double)(random.nextFloat() - random.nextFloat());
                        double y = getY() + (double)(random.nextFloat() * 0.5F);
                        double z = getZ() + (double)(random.nextFloat() - random.nextFloat());
                        double xxx = xx * 0.15D + (double)(random.nextFloat() * 0.05F);
                        double yyy = yy * 0.15D + (double)(random.nextFloat() * 0.05F);
                        double zzz = zz * 0.15D + (double)(random.nextFloat() * 0.05F);
                        blaze.moveTo(x, y, z, getYRot(), 0.0F);
                        blaze.setDeltaMovement(xxx, yyy, zzz);
                        blaze.setTarget(getTarget());
                        level.addFreshEntity(blaze);
                    }
                }
            }
        }

    }

    //发射火焰流星雨
    class SpawnFlameRainGoal extends Goal {

        private int counter = 0;

        @Override
        public boolean canUse() {
            return getTarget() != null && distanceToSqr(getTarget()) <= 1024.0D && random.nextFloat() * 100.0F < 1.5F;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = getTarget();
            return livingentity != null && livingentity.isAlive() && canAttack(livingentity);
        }

        @Override
        public void tick() {
            this.counter++;
            LivingEntity livingentity = getTarget();
            boolean higherThanHalfHealth = getHealth() > getMaxHealth() / 2.0F;
            if (higherThanHalfHealth && isOnFire() && this.counter % 5 == 0 && livingentity != null) {
                if (distanceTo(livingentity) > 30.0D || distanceTo(livingentity) < 8.0D) {
                    spawnFlameShower();
                    teleportToSightOfEntity(getTarget());
                }
            }
        }

    }

    //召唤特殊烈焰人作为能量源
    class SpawnPowerSourceGoal extends Goal {

        @Override
        public boolean canUse() {
            return getTarget() != null && distanceToSqr(getTarget()) <= 1024.0D && random.nextFloat() * 100.0F < 0.6F;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = getTarget();
            return livingentity != null && livingentity.isAlive() && canAttack(livingentity);
        }

        @Override
        public void tick() {
            LivingEntity livingentity = getTarget();
            boolean lowHealth = getHealth() < getMaxHealth() * 0.6F;
            int blazeCount = getBlazeAround(32.0D, IS_POWER_SOURCE).size();
            if (blazeCount == 0 && lowHealth && isOnFire() && livingentity != null && distanceTo(livingentity) < 24.0D) {
                if (isOnGround()) {
                    moveTo(getX(), getY() + 5.0D, getZ());
                }
                for (int i = (int)Math.ceil((double)(getHealth() / getMaxHealth()) * 5.0D); i > 0; --i) {
                    AncientBlaze blaze = EPEntity.ANCIENT_BLAZE.get().create(level);
                    if (blaze != null) {
                        double x = getX() + (double)(random.nextFloat() - random.nextFloat());
                        double y = getY() + (double)(random.nextFloat() * 0.5F);
                        double z = getZ() + (double)(random.nextFloat() - random.nextFloat());
                        blaze.moveTo(x, y, z, getYRot(), 0.0F);
                        blaze.setTarget(getTarget());
                        blaze.setPowerSource(true);
                        level.addFreshEntity(blaze);
                    }
                }
            }
        }

    }

    //发射小爆裂弹
    class ShootSmallCrackGoal extends Goal {

        private final WildfireEntity wildfire;
        public int chargeTime;

        public ShootSmallCrackGoal(WildfireEntity wildfire) {
            this.wildfire = wildfire;
        }

        public boolean canUse() {
            return this.wildfire.getTarget() != null;
        }

        public void start() {
            this.chargeTime = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.wildfire.getTarget();
            if (livingentity != null) {
                if (livingentity.distanceToSqr(this.wildfire) < 4096.0D && this.wildfire.hasLineOfSight(livingentity)) {
                    Level level = this.wildfire.level;
                    ++this.chargeTime;
                    if (this.chargeTime == 10 && !this.wildfire.isSilent()) {
                        level.levelEvent(null, 1018, this.wildfire.blockPosition(), 0);
                    }

                    if (this.chargeTime == 30) {
                        Vec3 vec3 = this.wildfire.getViewVector(1.0F);
                        double d2 = livingentity.getX() - (this.wildfire.getX() + vec3.x * 4.0D);
                        double d3 = livingentity.getY(0.5D) - (0.5D + this.wildfire.getY(0.5D));
                        double d4 = livingentity.getZ() - (this.wildfire.getZ() + vec3.z * 4.0D);
                        if (!this.wildfire.isSilent()) {
                            level.levelEvent(null, 1018, this.wildfire.blockPosition(), 0);
                        }

                        SmallCrack smallCrack = new SmallCrack(level, this.wildfire, d2, d3, d4);
                        double x = this.wildfire.getX() + vec3.x * 5.0D;
                        double y = this.wildfire.getY(0.5D) + 0.5D;
                        double z = smallCrack.getZ() + vec3.z * 5.0D;
                        smallCrack.setExplosionPower(entityData.get(SMALL_CRACK_POWER));
                        smallCrack.setSharedFlagOnFire(true);
                        smallCrack.setSecondsOnFire(10);
                        smallCrack.setPos(x, y, z);
                        level.addFreshEntity(smallCrack);
                        this.chargeTime = -40;
                    }
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }
            }
        }
    }

    //发射回旋火焰弹
    class ShootBoomerangFireball extends Goal {

        private int counter = 0;
        private final WildfireEntity wildfire;

        public ShootBoomerangFireball(WildfireEntity wildfire) {
            this.wildfire = wildfire;
        }

        @Override
        public boolean canUse() {
            return getTarget() != null && distanceToSqr(getTarget()) <= 1024.0D && random.nextFloat() * 100.0F < 1.5F;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = getTarget();
            return livingentity != null && livingentity.isAlive() && canAttack(livingentity);
        }

        @Override
        public void tick() {
            this.counter++;
            LivingEntity target = this.wildfire.getTarget();
            int boomerangCount = this.getBoomerangAround(16.0D).size();
            if (target != null && boomerangCount == 0 && counter % 4 == 0 && distanceTo(target) < 12.0D) {
                launchFireball(true, true);
                launchFireball(false, true);
                launchFireball(true, false);
                launchFireball(false, false);
            }
        }

        public List<BoomerangFireball> getBoomerangAround(double radius) {
            BlockPos blockpos = wildfire.getOnPos();
            AABB aabb = (new AABB(blockpos)).inflate(radius);
            return level.getEntitiesOfClass(BoomerangFireball.class, aabb);
        }

        //两个布尔值形参用于控制射出的x与z方向是否取正
        private void launchFireball(boolean positiveX, boolean positiveZ) {
            float bodyFacingAngle = ((wildfire.yBodyRot * Mth.PI) / 90F);
            float pitch = (wildfire.getRandom().nextFloat() - wildfire.getRandom().nextFloat()) * 0.2F + 1.0F;
            double ry = (wildfire.getY() + wildfire.getBbHeight() / 2.0F);
            double sx = getX() + (Mth.cos(bodyFacingAngle) * 0.65D);
            double sy = getY() + (wildfire.getBbHeight() * 0.60D);
            double sz = getZ() + (Mth.sin(bodyFacingAngle) * 0.65D);
            double tx = Objects.requireNonNull(wildfire.getTarget()).getX() - sx;
            double ty = (wildfire.getTarget().getBoundingBox().minY + wildfire.getTarget().getBbHeight() / 2.0F) - ry;
            double tz = wildfire.getTarget().getZ() - sz;
            double ux = positiveX ? tx : -tx;
            double uz = positiveZ ? tz : -tz;
            wildfire.playSound(SoundEvents.BLAZE_SHOOT, 1.5F, pitch);
            BoomerangFireball fireball = new BoomerangFireball(EPEntity.BLAZE_BOOMERANG.get(), level);
            fireball.setNoGravity(true);
            fireball.setOwner(this.wildfire);
            fireball.moveTo(sx, sy, sz, getYRot(), getXRot());
            fireball.shoot(ux, ty, uz, 1.0F, 0.5F);
            wildfire.getLevel().addFreshEntity(fireball);
        }

    }

    //连续发射火球
    class FireballFusilladeGoal extends Goal {

        private int counter = 0;
        private final WildfireEntity wildfire;

        public FireballFusilladeGoal(WildfireEntity entity) {
            wildfire = entity;
        }

        @Override
        public boolean canUse() {
            return this.wildfire.getTarget() != null && distanceToSqr(this.wildfire.getTarget()) <= 1024.0D && random.nextFloat() * 100.0F < 0.6F;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.wildfire.getTarget();
            return livingentity != null && livingentity.isAlive() && this.wildfire.canAttack(livingentity);
        }

        @Override
        public void tick() {
            this.counter++;
            Level level = this.wildfire.level;
            LivingEntity livingentity = this.wildfire.getTarget();
            if (livingentity != null && this.counter % 2 == 0 && this.counter <= 40 && distanceTo(livingentity) < 12.0D) {
                FallingFireball fireball = new FallingFireball(EPEntity.FALLING_FIREBALL.get(), level);
                launchProjectile(fireball);
            }
        }

    }

}