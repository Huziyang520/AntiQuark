package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, enabledByDefault = false, name = "Passive Mobs Cannot Ride", description = "Passive animals cannot board boats or minecarts.")
public class PassiveNoRideModule extends ZetaModule {
    @ModuleInstance
    public static PassiveNoRideModule INSTANCE;
}
