package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
 import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobLeashMixin {
    @Inject(method = "canBeLeashed(Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"), cancellable = true)
    private void antiquark$leashAnyMob(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.gameplay.LeashAnyMobModule.class)) {
            cir.setReturnValue(true);
        }
    }
}
