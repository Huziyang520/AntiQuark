package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.ZombieVillagerConversionModule;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Zombie.class)
public abstract class ZombieKilledEntityMixin {
    /**
     * Vanilla rolls a coin flip to skip villager conversion below hard difficulty; failing the roll keeps
     * every other part of the vanilla conversion path intact.
     */
    @Redirect(method = "killedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextBoolean()Z"), require = 1)
    private boolean antiquark$alwaysConvert(RandomSource random) {
        return !AntiQuark.ZETA.modules.isEnabled(ZombieVillagerConversionModule.class) && random.nextBoolean();
    }
}
