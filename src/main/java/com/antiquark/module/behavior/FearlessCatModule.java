package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import java.util.ArrayList;

import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Cat;
import org.violetmoon.zeta.config.Config;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Fearless Cats", description = "Some naturally spawned cats no longer flee from players.")
public class FearlessCatModule extends ZetaModule {
    private static final String FEARLESS_TAG = "antiquark:fearless_cat";

    @ModuleInstance
    public static FearlessCatModule INSTANCE;

    @Config(name = "Chance", description = "Chance for a newly spawned cat to be fearless.")
    public double chance = 0.5D;

    public static double chance() {
        return INSTANCE == null ? 0.5D : INSTANCE.chance;
    }

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (!(event.getEntity() instanceof Cat cat) || cat.level().isClientSide || cat.isTame()) {
            return;
        }
        if (!cat.getPersistentData().contains(FEARLESS_TAG)) {
            cat.getPersistentData().putBoolean(FEARLESS_TAG, cat.level().random.nextDouble() < chance());
        }
        if (isFearless(cat)) {
            removePlayerAvoidanceGoal(cat);
        }
    }

    public static boolean isFearless(Cat cat) {
        return cat.getPersistentData().getBoolean(FEARLESS_TAG);
    }

    private static void removePlayerAvoidanceGoal(Cat cat) {
        for (WrappedGoal wrapped : new ArrayList<>(cat.goalSelector.getAvailableGoals())) {
            if (wrapped.getGoal().getClass().getName().equals("net.minecraft.world.entity.animal.Cat$CatAvoidEntityGoal")) {
                cat.goalSelector.removeGoal(wrapped.getGoal());
            }
        }
    }
}
