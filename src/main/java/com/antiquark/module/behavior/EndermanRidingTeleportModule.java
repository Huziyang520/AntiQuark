package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.monster.EnderMan;
import com.antiquark.mixin.server.EnderManTeleportHelper;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingTick;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Endermen Escape Boats", description = "Endermen riding boats or minecarts teleport away.")
public class EndermanRidingTeleportModule extends ZetaModule {
    @ModuleInstance
    public static EndermanRidingTeleportModule INSTANCE;

    @PlayEvent
    public void onTick(ZLivingTick event) {
        if (event.getEntity() instanceof EnderMan enderman && enderman.isPassenger() && !enderman.level().isClientSide) {
            EnderManTeleportHelper.teleport(enderman);
        }
    }
}
