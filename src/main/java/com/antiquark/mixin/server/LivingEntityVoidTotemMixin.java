package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityVoidTotemMixin {
    @Inject(method = "checkTotemDeathProtection", at = @At("RETURN"), cancellable = true)
    private void antiquark$voidTotem(net.minecraft.world.damagesource.DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (cir.getReturnValueZ() || !AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.gameplay.VoidTotemModule.class)) {
            return;
        }
        if (source.is(net.minecraft.world.damagesource.DamageTypes.FELL_OUT_OF_WORLD)) {
            cir.setReturnValue(VoidTotemHelper.tryVoidTotem(self));
        }
    }
}
