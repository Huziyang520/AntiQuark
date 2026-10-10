package com.antiquark.module.behavior;

import com.antiquark.AntiQuark;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;

public final class BehaviorHooks {
    private BehaviorHooks() {
    }

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }
        if (AntiQuark.ZETA.modules.isEnabled(WolfTargetsSkeletonHorseModule.class)
                && living instanceof Wolf wolf) {
            wolf.targetSelector.addGoal(2, new WolfAttackSkeletonHorseGoal(wolf));
        }
    }
}
