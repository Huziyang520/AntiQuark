package com.antiquark.module.behavior;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Cat;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;

public final class FearlessCatJoinHook {
    private FearlessCatJoinHook() {
    }

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (!(event.getEntity() instanceof LivingEntity living)
                || com.antiquark.module.behavior.AntiQuarkZetaHelper.disabled(FearlessCatModule.class)) {
            return;
        }
        if (living instanceof Cat cat && cat.level().random.nextFloat() < FearlessCatModule.chance()) {
            cat.goalSelector.addGoal(6, new CatNoAvoidGoal(cat));
        }
    }

    private static final class CatNoAvoidGoal extends Goal {
        private final Cat cat;

        private CatNoAvoidGoal(Cat cat) {
            this.cat = cat;
        }

        @Override
        public boolean canUse() {
            return false;
        }
    }
}
