package com.antiquark.mixin.server;

import com.antiquark.module.behavior.FearlessCatModule;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public abstract class CatMixin {
    @Inject(method = "reassessTameGoals", at = @At("HEAD"), cancellable = true)
    private void antiquark$skipPlayerAvoidance(CallbackInfo ci) {
        if (FearlessCatModule.isFearless((Cat) (Object) this)) {
            ci.cancel();
        }
    }
}
