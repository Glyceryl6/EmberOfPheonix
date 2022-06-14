package com.glyceryl.emberphoenix.common.entity.monster;

import com.glyceryl.emberphoenix.common.entity.ai.WildFireAttackGoal;
import com.glyceryl.emberphoenix.common.entity.projectile.SmallCrack;
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
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class WildfireEntity extends Monster {

    private static final EntityDataAccessor<Byte> ON_FIRE = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SHIELDING = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WildfireEntity.class, EntityDataSerializers.BOOLEAN);

    //给BOSS添加一个黄色的血条
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    public boolean shieldDisabled = false;

    public WildfireEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new WildFireAttackGoal(this));
        this.goalSelector.addGoal(1, new WildfireEntity.SpawnMinionsGoal());
        this.goalSelector.addGoal(4, new WildfireEntity.ShootSmallCrackGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_32235_) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 1.8F;
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    //死亡时灭掉周围所有的火，并杀死周围所有的烈焰人
    private void removeFireAndBlazes() {
        BlockPos blockpos = this.getOnPos();
        AABB aabb = (new AABB(blockpos)).inflate(48.0D);
        for (BlockPos pos : BlockPos.withinManhattan(blockpos, 32, 16, 32)) {
            if (level.getBlockState(pos).is(Blocks.FIRE)) {
                level.removeBlock(pos, false);
            }
        }
        for(Blaze blaze : this.level.getEntitiesOfClass(Blaze.class, aabb)) {
            BlockPos blazePos = blaze.getOnPos();
            double d0 = blazePos.getX();
            double d1 = blazePos.getY();
            double d2 = blazePos.getZ();
            int c = 4;
            for(int i = -c; i <= c; ++i) {
                for(int j = -c; j <= c; ++j) {
                    for(int k = -c; k <= c; ++k) {
                        double d3 = (double)j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d4 = (double)i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d5 = (double)k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / 0.5D + this.random.nextGaussian() * 0.05D;
                        this.level.addParticle(ParticleTypes.FLAME, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                        if (i != -c && i != c && j != -c && j != c) {
                            k += c * 2 - 1;
                        }
                    }
                }
            }
            blaze.kill();
        }
    }

    //检测跟踪范围内是否存在烈焰人，有则回血
    private void checkBlazes() {
        int blazeCount = this.getBlazeAround(this.getAttributeValue(Attributes.FOLLOW_RANGE)).size();
        if (this.tickCount % 10 == 0 && this.getHealth() < this.getMaxHealth() && blazeCount > 0) {
            this.heal(1.0F);
        }
    }

    @Override
    protected void tickDeath() {
        this.removeFireAndBlazes();
        super.tickDeath();
    }

    public void aiStep() {
        if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.level.isClientSide) {
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
            for (int i = 0; i < 16; i++) {
                this.level.addParticle(ParticleTypes.LAVA, getRandomX(0.75D), this.getRandomY(), this.getRandomZ(0.75D), 0.0D, 0.0D, 0.0D);
            }
        }
        this.checkBlazes();
        super.aiStep();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.entityData.get(VARIANT));
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
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

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }
    
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHIELDING, Boolean.FALSE);
        this.entityData.define(ATTACKING, Boolean.FALSE);
        this.entityData.define(ON_FIRE, (byte) 0);
        this.entityData.define(VARIANT, 0);
    }

    //获取一定范围内的烈焰人数量
    private List<Blaze> getBlazeAround(double radius) {
        BlockPos blockpos = this.getOnPos();
        AABB aabb = (new AABB(blockpos)).inflate(radius);
        return level.getEntitiesOfClass(Blaze.class, aabb);
    }

    public void setShielding(boolean shielding) {
        if (!this.shieldDisabled) {
            this.entityData.set(SHIELDING, shielding);
        } else {
            this.entityData.set(SHIELDING, Boolean.FALSE);
        }
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

    public boolean isOnFire() {
        return (this.entityData.get(ON_FIRE) & 1) != 0;
    }

    public void setSharedFlagOnFire(boolean onFire) {
        byte b0 = this.entityData.get(ON_FIRE);
        if (onFire) {
            b0 = (byte)(b0 | 0x1);
        } else {
            b0 = (byte)(b0 & 0xFFFFFFFE);
        }
        this.entityData.set(ON_FIRE, b0);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (!this.level.isClientSide) {
            Entity entity = source.getDirectEntity();
            if (entity instanceof LivingEntity livingEntity) {
                if (isInvulnerable()) {
                    if (livingEntity.getMainHandItem().getItem() instanceof AxeItem) {
                        double itemDamage = (((AxeItem) livingEntity.getMainHandItem().getItem()).getAttackDamage() + 1.0F);
                        if (amount >= itemDamage + itemDamage / 2.0D) {
                            playSound(SoundEvents.ANVIL_PLACE, 0.3F, 1.5F);
                            this.shieldDisabled = true;
                            setShielding(false);
                            setInvulnerable(false);
                            return false;
                        }
                    }
                }
            }
            if (isInvulnerableTo(source)) {
                playSound(SoundEvents.ANVIL_PLACE, 0.3F, 0.5F);
                if (source.getDirectEntity() != null)
                    if (source.isProjectile()) {
                        source.getDirectEntity().setSecondsOnFire(12);
                    } else {
                        source.getDirectEntity().setSecondsOnFire(8);
                    }
                return false;
            }
        }
        return super.hurt(source, amount);
    }

    public boolean isInvulnerableTo(DamageSource source) {
        if ((source == DamageSource.GENERIC) && !source.isCreativePlayer())
            return isInvulnerable();
        return false;
    }

    //召唤一定数量的烈焰人
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
            int blazeCount = getBlazeAround(64.0D).size();
            if (isOnFire() && blazeCount < 10) {
                double xx = getTarget().getX() - getX();
                double yy = getTarget().getY() - getY();
                double zz = getTarget().getZ() - getZ();
                for (int i = (int)Math.ceil((double)(getHealth() / getMaxHealth()) * 5.0D); i > 0; --i) {
                    Blaze blaze = EntityType.BLAZE.create(level);
                    double x = getX() + (double)(random.nextFloat() - random.nextFloat());
                    double y = getY() + (double)(random.nextFloat() * 0.5F);
                    double z = getZ() + (double)(random.nextFloat() - random.nextFloat());
                    double xxx = xx * 0.15D + (double)(random.nextFloat() * 0.05F);
                    double yyy = yy * 0.15D + (double)(random.nextFloat() * 0.05F);
                    double zzz = zz * 0.15D + (double)(random.nextFloat() * 0.05F);
                    blaze.setDeltaMovement(xxx, yyy, zzz);
                    blaze.moveTo(x, y, z, getYRot(), 0.0F);
                    blaze.setTarget(getTarget());
                    level.addFreshEntity(blaze);
                }
            }
        }

    }

    //发射小爆裂弹
    class ShootSmallCrackGoal extends Goal {

        private final WildfireEntity wildfire;
        public int chargeTime;

        public ShootSmallCrackGoal(WildfireEntity p_32776_) {
            this.wildfire = p_32776_;
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

                    if (isOnFire() || this.chargeTime == 40) {
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
                        smallCrack.setSecondsOnFire(Integer.MAX_VALUE);
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

}