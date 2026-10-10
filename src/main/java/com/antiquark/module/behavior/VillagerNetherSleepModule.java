package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZSleepingLocationCheck;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Villagers Explode in the Nether", description = "Villagers attempting to sleep in the Nether or End explode.")
public class VillagerNetherSleepModule extends ZetaModule {
    @ModuleInstance
    public static VillagerNetherSleepModule INSTANCE;

    @PlayEvent
    public void onSleepCheck(ZSleepingLocationCheck event) {
        Level level = event.getEntity().level();
        if (!(event.getEntity() instanceof Villager villager) || level.isClientSide) {
            return;
        }
        if (level.dimension() == Level.NETHER || level.dimension() == Level.END) {
            villager.getSleepingPos().ifPresent(pos -> {
                level.explode(villager, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, Level.ExplosionInteraction.MOB);
            });
        }
    }
}
