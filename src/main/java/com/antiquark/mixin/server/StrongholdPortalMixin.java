package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces.PortalRoom.class)
public abstract class StrongholdPortalMixin {
    @Inject(method = "placeBlock", at = @At("HEAD"), cancellable = true)
    private void antiquark$containEndPortal(net.minecraft.world.level.WorldGenLevel level, BlockState state, int x, int y, int z, net.minecraft.world.level.levelgen.structure.BoundingBox box, CallbackInfo ci) {
        if (state.is(Blocks.END_PORTAL)
                && AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.world.EndPortalPlacementModule.class)) {
            boolean inside = x >= 4 && x <= 6 && y == 3 && z >= 9 && z <= 11;
            if (!inside) {
                ci.cancel();
            }
        }
    }
}
