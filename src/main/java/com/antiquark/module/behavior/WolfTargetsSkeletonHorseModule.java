package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Wolf Targets Skeleton Horses", description = "Untamed wolves consider skeleton horses valid prey.")
public class WolfTargetsSkeletonHorseModule extends ZetaModule {
    private static final String APPLIED_TAG = "antiquark:wolf_targets_skeleton_horses";

    @ModuleInstance
    public static WolfTargetsSkeletonHorseModule INSTANCE;

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (!(event.getEntity() instanceof Wolf wolf) || wolf.level().isClientSide
                || wolf.getPersistentData().getBoolean(APPLIED_TAG)) {
            return;
        }
        wolf.getPersistentData().putBoolean(APPLIED_TAG, true);
        wolf.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(wolf, SkeletonHorse.class, false,
                WolfTargetsSkeletonHorseModule::isNotPlayerMounted));
    }

    private static boolean isNotPlayerMounted(LivingEntity target) {
        if (!(target instanceof SkeletonHorse horse)) {
            return false;
        }
        Entity passenger = horse.getFirstPassenger();
        return !(passenger instanceof Player);
    }
}
