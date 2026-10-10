package com.antiquark.mixin.server;

import com.antiquark.AntiQuark;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieKilledEntityMixin {
    @Inject(method = "killedEntity", at = @At("HEAD"), cancellable = true)
    private void antiquark$guaranteeConversion(ServerLevel level, LivingEntity victim, CallbackInfoReturnable<Boolean> cir) {
        if (AntiQuark.ZETA.modules.isEnabled(com.antiquark.module.gameplay.ZombieVillagerConversionModule.class)
                && victim instanceof Villager) {
            cir.setReturnValue(convertVillagerGuaranteed(level, (Zombie) (Object) this, (Villager) victim));
        }
    }

    private static boolean convertVillagerGuaranteed(ServerLevel level, Zombie zombie, Villager villager) {
        net.minecraft.world.entity.monster.ZombieVillager zv = villager.convertTo(net.minecraft.world.entity.EntityType.ZOMBIE_VILLAGER, false);
        if (zv != null) {
            zv.finalizeSpawn(level, level.getCurrentDifficultyAt(zv.blockPosition()), net.minecraft.world.entity.MobSpawnType.CONVERSION, null, null);
            zv.setVillagerData(villager.getVillagerData());
            zv.setGossips(villager.getGossips().store(net.minecraft.nbt.NbtOps.INSTANCE));
            zv.setTradeOffers(villager.getOffers().createTag());
            zv.setVillagerXp(villager.getVillagerXp());
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(villager, zv);
            if (!zombie.isSilent()) {
                level.levelEvent((net.minecraft.world.entity.player.Player) null, 1026, zombie.blockPosition(), 0);
            }
            return true;
        }
        return false;
    }
}
