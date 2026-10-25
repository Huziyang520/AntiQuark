package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.behavior.HostileNoRideModule;
import com.antiquark.module.behavior.PassiveNoRideModule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityStartRidingMixin {
    @Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z", at = @At("HEAD"), cancellable = true)
    private void antiquark$blockVehicleBoarding(Entity vehicle, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (force || !(vehicle instanceof Boat) && !(vehicle instanceof AbstractMinecart)) {
            return;
        }
        Entity self = (Entity) (Object) this;
        if (!(self instanceof Mob)) {
            return;
        }
        boolean hostile = self instanceof Monster && AntiQuark.ZETA.modules.isEnabled(HostileNoRideModule.class);
        boolean passive = self instanceof Animal && AntiQuark.ZETA.modules.isEnabled(PassiveNoRideModule.class);
        if (hostile || passive) {
            cir.setReturnValue(false);
        }
    }
}
