package com.glyceryl.emberphoenix.mixin;

import com.glyceryl.emberphoenix.registry.EPItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class MixinPlayerModel<T extends LivingEntity> extends HumanoidModel<T> {

    @Shadow @Final public ModelPart rightSleeve;

    @Shadow @Final public ModelPart leftSleeve;

    public MixinPlayerModel(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim*", at = @At("TAIL"))
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo cir) {
        ItemStack mainHandItem = entity.getMainHandItem();
        ItemStack offHandItem = entity.getOffhandItem();
        if (mainHandItem.is(EPItems.CINDER_CANNON.get())) {
            this.rightSleeve.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
            this.rightSleeve.yRot = 0.0F;
            this.rightArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
            this.rightArm.yRot = 0.0F;
        }
        if (offHandItem.is(EPItems.CINDER_CANNON.get())) {
            this.leftSleeve.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
            this.leftSleeve.yRot = 0.0F;
            this.leftArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
            this.leftArm.yRot = 0.0F;
        }
    }

}
