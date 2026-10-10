package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class EnderManMixin {
    @Inject(method = "teleport()Z", at = @At("HEAD"), cancellable = true)
    private void antiquark$allowUnconditionalTeleport(CallbackInfoReturnable<Boolean> cir) {
        if (AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.behavior.EndermanRidingTeleportModule.class)
                && ((EnderMan) (Object) this).isPassenger()) {
            cir.setReturnValue(true);
        }
    }
}
