package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void antiquark$breakFragileBlocks(CallbackInfo ci) {
        FallingBlockEntity self = (FallingBlockEntity) (Object) this;
        if (self.level().isClientSide) {
            return;
        }
        BlockPos below = self.blockPosition().below();
        BlockState belowState = self.level().getBlockState(below);
        boolean dripleaf = belowState.is(Blocks.BIG_DRIPLEAF);
        boolean turtleEgg = belowState.is(Blocks.TURTLE_EGG);
        boolean doDripleaf = dripleaf && AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.world.FallingBlockDripleafModule.class);
        boolean doEgg = turtleEgg && AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.world.FallingBlockTurtleEggModule.class);
        if (doDripleaf || doEgg) {
            self.level().destroyBlock(below, doEgg, self);
        }
    }
}
