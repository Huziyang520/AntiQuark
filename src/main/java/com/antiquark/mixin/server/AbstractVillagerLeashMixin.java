package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.LeashVillagersModule;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerLeashMixin {
    @Inject(method = "canBeLeashed()Z", at = @At("HEAD"), cancellable = true)
    private void antiquark$leashVillagers(CallbackInfoReturnable<Boolean> cir) {
        Mob self = (Mob) (Object) this;
        if (!self.isLeashed() && AntiQuark.ZETA.modules.isEnabled(LeashVillagersModule.class)) {
            cir.setReturnValue(true);
        }
    }
}
