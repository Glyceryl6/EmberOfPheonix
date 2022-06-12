package com.glyceryl.emberphoenix.common.entity.ai;

import com.glyceryl.emberphoenix.common.entity.monster.WildfireEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.SmallFireball;

import java.util.EnumSet;

import static java.lang.Math.*;

public class WildFireAttackGoal extends Goal {

    private final WildfireEntity wildfire;
    private int attackStep;
    private int attackTime;
    private int firedRecentlyTimer;

    public WildFireAttackGoal(WildfireEntity wildfireIn) {
        this.wildfire = wildfireIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.wildfire.getTarget();
        return livingentity != null && livingentity.isAlive() && this.wildfire.canAttack(livingentity);
    }

    public void start() {
        this.attackStep = 0;
    }

    public void stop() {
        this.wildfire.setSharedFlagOnFire(false);
        this.wildfire.setShielding(false);
        this.wildfire.setAggressive(false);
        this.firedRecentlyTimer = 0;
    }

    @SuppressWarnings("all")
    public void tick() {
        --this.attackTime;
        LivingEntity livingEntity = this.wildfire.getTarget();
        this.wildfire.setAggressive(false);
        if (livingEntity != null) {
            boolean flag = this.wildfire.getSensing().hasLineOfSight(livingEntity);
            if (flag) {
                this.firedRecentlyTimer = 0;
            } else {
                ++this.firedRecentlyTimer;
            }

            double d0 = this.wildfire.distanceToSqr(livingEntity);
            if (d0 < 4.0D) {

                this.wildfire.setSharedFlagOnFire(true);

                if (this.attackTime <= 0) {
                    this.wildfire.setAggressive(true);
                    this.attackTime = 5;
                    this.wildfire.doHurtTarget(livingEntity);
                    livingEntity.setSecondsOnFire(4);
                }

                this.wildfire.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                double d1 = livingEntity.getX() - this.wildfire.getX();
                double d2 = livingEntity.getY(0.5D) - this.wildfire.getY(0.5D);
                double d3 = livingEntity.getZ() - this.wildfire.getZ();

                float health = (this.wildfire.getMaxHealth() - this.wildfire.getHealth()) / 2;
                float healthPercent = this.wildfire.getHealth() / this.wildfire.getMaxHealth();

                int maxAttackSteps = 3;

                if (d0 < 36.0D) {
                    ++maxAttackSteps;
                }
                if (healthPercent < 0.6) {
                    ++maxAttackSteps;
                }

                if (this.attackTime <= 0) {
                    this.wildfire.setShielding(false);
                    ++this.attackStep;
                    if (this.attackStep == 1) {
                        this.attackTime = (int) (40 * healthPercent + 20);
                        this.wildfire.setSharedFlagOnFire(true);
                    } else if (this.attackStep <= maxAttackSteps) {
                        this.attackTime = (int) (25 * healthPercent + 5);
                    } else {
                        this.attackTime = 200;
                        this.attackStep = 0;
                        this.wildfire.setSharedFlagOnFire(false);
                        this.wildfire.setAggressive(false);
                    }

                    if (this.attackStep > 1) {

                        this.wildfire.setAggressive(true);

                        if (!this.wildfire.isSilent()) {
                            this.wildfire.level.playSound(null, this.wildfire.getOnPos(), SoundEvents.BLAZE_SHOOT, this.wildfire.getSoundSource(), 1.0F, 1.0F);
                        }

                        double fireballCount = 17;
                        double offsetangle = toRadians(4.0F);
                        double maxdepressangle = toRadians(50.0F);

                        //update target pos
                        d1 = livingEntity.getX() - this.wildfire.getX();
                        d2 = livingEntity.getY(0.5D) - this.wildfire.getY(0.5D);
                        d3 = livingEntity.getZ() - this.wildfire.getZ();

                        //shoot fireballs
                        for (int i = 0; i <= (fireballCount - 1); ++i) {
                            SmallFireball smallFireball;
                            double angle = (i - ((fireballCount - 1) / 2)) * offsetangle;
                            double x = d1 * cos(angle) + d3 * sin(angle);
                            double y = d2;
                            double z = -d1 * sin(angle) + d3 * cos(angle);
                            double sqrt = sqrt((d1 * d1) + (d3 * d3));
                            if (abs((atan2(d2, sqrt))) > maxdepressangle) {
                                y = -tan(maxdepressangle) * sqrt;
                            }
                            smallFireball = new SmallFireball(this.wildfire.level, this.wildfire, x, y, z);
                            smallFireball.setPos(smallFireball.getX(), this.wildfire.getY(0.5D), smallFireball.getZ());
                            this.wildfire.level.addFreshEntity(smallFireball);
                        }
                    }
                } else if (this.attackTime < 160 + health && this.attackTime > 90 - health) {
                    this.wildfire.setShielding(true);
                } else if (this.attackTime >= 30 && this.attackTime >= 50) {
                    this.wildfire.setShielding(false);
                    this.wildfire.shieldDisabled = false;
                }

                this.wildfire.setInvulnerable(this.wildfire.getShielding());
                this.wildfire.getLookControl().setLookAt(livingEntity, 10.0F, 10.0F);
            } else if (this.firedRecentlyTimer < 5) {
                this.wildfire.getMoveControl().setWantedPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0D);
            }

            super.tick();
        }
    }

    private double getFollowDistance() {
        return this.wildfire.getAttributeValue(Attributes.FOLLOW_RANGE);
    }
}