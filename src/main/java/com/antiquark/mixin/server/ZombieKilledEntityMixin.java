package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.ZombieVillagerConversionModule;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Zombie.class)
public abstract class ZombieKilledEntityMixin {

    @Redirect(method = "killedEntity", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getDifficulty()Lnet/minecraft/world/Difficulty;"), require = 1)
    private Difficulty antiquark$convertOnEasyToo(ServerLevel level) {
        Difficulty actual = level.getDifficulty();
        if (actual == Difficulty.PEACEFUL || !AntiQuark.ZETA.modules.isEnabled(ZombieVillagerConversionModule.class)) {
            return actual;
        }
        return Difficulty.NORMAL;
    }

    @Redirect(method = "killedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextBoolean()Z"), require = 1)
    private boolean antiquark$alwaysConvert(RandomSource random) {
        return !AntiQuark.ZETA.modules.isEnabled(ZombieVillagerConversionModule.class) && random.nextBoolean();
    }
}
