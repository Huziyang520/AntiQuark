package com.antiquark.module.behavior;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;

public final class WolfAttackSkeletonHorseGoal extends Goal {
    private final Wolf wolf;

    public WolfAttackSkeletonHorseGoal(Wolf wolf) {
        this.wolf = wolf;
    }

    @Override
    public boolean canUse() {
        return wolf.getTarget() instanceof SkeletonHorse;
    }
}
