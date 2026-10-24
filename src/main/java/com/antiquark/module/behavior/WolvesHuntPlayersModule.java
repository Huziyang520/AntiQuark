package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import org.violetmoon.zeta.config.Config;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Wolves Hunt Players", description = "A chance for untamed wolves to treat players as prey.")
public class WolvesHuntPlayersModule extends ZetaModule {
    private static final String APPLIED_TAG = "antiquark:wolf_hunts_players";

    @ModuleInstance
    public static WolvesHuntPlayersModule INSTANCE;

    @Config(name = "Chance", description = "Chance for a newly spawned untamed wolf to hunt players. 0 disables this feature.")
    @Config.Min(0.0D)
    @Config.Max(1.0D)
    public double chance = 0.0D;

    public static double chance() {
        WolvesHuntPlayersModule module = INSTANCE;
        return module == null ? 0.0D : module.chance;
    }

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (!(event.getEntity() instanceof Wolf wolf) || wolf.level().isClientSide
                || wolf.isTame() || wolf.isBaby()
                || wolf.getPersistentData().getBoolean(APPLIED_TAG)) {
            return;
        }
        wolf.getPersistentData().putBoolean(APPLIED_TAG, true);

        double chance = chance();
        if (chance <= 0.0D || wolf.getRandom().nextDouble() >= chance) {
            return;
        }
        wolf.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(wolf, Player.class, true));
    }
}
