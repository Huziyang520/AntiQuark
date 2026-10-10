package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingTick;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, enabledByDefault = false, name = "Hostile Mobs Cannot Ride", description = "Hostile mobs dismount from boats and minecarts.")
public class HostileNoRideModule extends ZetaModule {
    @ModuleInstance
    public static HostileNoRideModule INSTANCE;

    @PlayEvent
    public void onTick(ZLivingTick event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Monster && entity.isPassenger() && !entity.level().isClientSide) {
            entity.stopRiding();
        }
    }
}
