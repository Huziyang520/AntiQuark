package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingTick;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, enabledByDefault = false, name = "Passive Mobs Cannot Ride", description = "Passive animals dismount from boats and minecarts.")
public class PassiveNoRideModule extends ZetaModule {
    @ModuleInstance
    public static PassiveNoRideModule INSTANCE;

    @PlayEvent
    public void onTick(ZLivingTick event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Animal && entity.isPassenger() && !entity.level().isClientSide) {
            entity.stopRiding();
        }
    }
}
