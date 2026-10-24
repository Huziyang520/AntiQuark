package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.player.ZPlayer;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Mining While Riding", description = "Mining from a boat or minecart is as fast as mining on foot.")
public class MineWhileRidingModule extends ZetaModule {
    @ModuleInstance
    public static MineWhileRidingModule INSTANCE;

    @PlayEvent
    public void onBreakSpeed(ZPlayer.BreakSpeed event) {
        Player player = event.getPlayer();
        if (player.isPassenger() && (player.getVehicle() instanceof Boat || player.getVehicle() instanceof AbstractMinecart)) {
            //The airborne mining penalty divides the speed by five, which is what riding a vehicle counts as
            event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
        }
    }
}
