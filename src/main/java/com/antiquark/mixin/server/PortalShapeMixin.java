package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.portal.PortalShape;

@Mixin(PortalShape.class)
public abstract class PortalShapeMixin {
    @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true, require = 1)
    private static void antiquark$treatPortalAsEmpty(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(Blocks.NETHER_PORTAL)
                && AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.world.NetherPortalOverlapModule.class)) {
            cir.setReturnValue(true);
        }
    }
}
