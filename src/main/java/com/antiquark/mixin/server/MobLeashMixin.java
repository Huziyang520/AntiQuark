package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.LeashAnyMobModule;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobLeashMixin {
    @Inject(method = "canBeLeashed()Z", at = @At("HEAD"), cancellable = true)
    private void antiquark$leashAnyMob(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof AbstractVillager) {
            return;
        }
        if (AntiQuark.ZETA.modules.isEnabled(LeashAnyMobModule.class)) {
            cir.setReturnValue(true);
        }
    }
}
