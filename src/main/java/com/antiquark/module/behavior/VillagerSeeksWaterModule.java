package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.ai.goal.Goal;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.ZEntityJoinLevel;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Villagers Seek Water", description = "Burning villagers try to reach nearby water.")
public class VillagerSeeksWaterModule extends ZetaModule {
    @ModuleInstance
    public static VillagerSeeksWaterModule INSTANCE;

    @PlayEvent
    public void onJoin(ZEntityJoinLevel event) {
        if (event.getEntity() instanceof Villager villager) {
            villager.goalSelector.addGoal(0, new SeekWaterGoal(villager));
        }
    }

    private static final class SeekWaterGoal extends Goal {
        private final Villager villager;

        private SeekWaterGoal(Villager villager) {
            this.villager = villager;
        }

        @Override
        public boolean canUse() {
            if (!villager.isOnFire() || villager.getNavigation().isDone()) {
                return false;
            }
            BlockPos target = null;
            for (BlockPos pos : BlockPos.withinManhattan(villager.blockPosition(), 12, 4, 12)) {
                if (villager.level().getFluidState(pos).is(FluidTags.WATER)
                        && villager.getNavigation().createPath(pos, 0) != null) {
                    target = pos.immutable();
                    break;
                }
            }
            if (target != null) {
                villager.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.1D);
            }
            return target != null;
        }

        @Override
        public boolean canContinueToUse() {
            return villager.isOnFire() && !villager.getNavigation().isDone();
        }
    }
}
