package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.LeashAnyMobModule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public abstract class SlimeLeashMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void antiquark$followLeash(CallbackInfo ci) {
        Slime self = (Slime) (Object) this;
        if (self.level().isClientSide || !AntiQuark.ZETA.modules.isEnabled(LeashAnyMobModule.class)) {
            return;
        }
        Entity holder = self.getLeashHolder();
        if (holder == null || holder.level() != self.level()) {
            return;
        }
        Vec3 offset = new Vec3(holder.getX() - self.getX(), 0.0D, holder.getZ() - self.getZ());
        double distance = offset.length();
        if (distance < 2.0D) {
            return;
        }
        double speed = Math.min(0.35D, distance * 0.12D);
        Vec3 step = offset.normalize().scale(speed);
        self.move(MoverType.SELF, step);
    }
}
