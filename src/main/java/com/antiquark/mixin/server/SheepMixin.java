package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.ColoredWoolModule;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sheep.class)
public abstract class SheepMixin {
    @Inject(method = "shear", at = @At("HEAD"), cancellable = true)
    private void antiquark$dropColoredWool(SoundSource source, CallbackInfo ci) {
        Sheep sheep = (Sheep) (Object) this;
        if (!AntiQuark.ZETA.modules.isEnabled(ColoredWoolModule.class)
                || !ColoredWoolModule.isRainbowSheep(sheep)
                || sheep.isSheared()
                || sheep.level().isClientSide) {
            return;
        }

        sheep.level().playSound(null, sheep, SoundEvents.SHEEP_SHEAR, source, 1.0F, 1.0F);
        sheep.setSheared(true);
        int count = 1 + sheep.getRandom().nextInt(3);
        for (int i = 0; i < count; i++) {
            ItemEntity drop = sheep.spawnAtLocation(new ItemStack(ColoredWoolModule.wool()));
            if (drop != null) {
                drop.setDeltaMovement(drop.getDeltaMovement().add(
                        (sheep.getRandom().nextFloat() - sheep.getRandom().nextFloat()) * 0.1F,
                        sheep.getRandom().nextFloat() * 0.05F,
                        (sheep.getRandom().nextFloat() - sheep.getRandom().nextFloat()) * 0.1F));
            }
        }
        ci.cancel();
    }
}
