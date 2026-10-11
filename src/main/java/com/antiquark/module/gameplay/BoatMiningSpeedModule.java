package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.vehicle.Boat;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.player.ZPlayer;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Mine From Boats", description = "Mining while in a boat is as fast as mining on foot.")
public class BoatMiningSpeedModule extends ZetaModule {
    @ModuleInstance
    public static BoatMiningSpeedModule INSTANCE;

    @PlayEvent
    public void onBreakSpeed(ZPlayer.BreakSpeed event) {
        if (event.getEntity().isPassenger() && event.getEntity().getVehicle() instanceof Boat) {
            event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
        }
    }
}
