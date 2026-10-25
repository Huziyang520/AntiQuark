package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, enabledByDefault = false, name = "Hostile Mobs Cannot Ride", description = "Hostile mobs cannot board boats or minecarts.")
public class HostileNoRideModule extends ZetaModule {
    @ModuleInstance
    public static HostileNoRideModule INSTANCE;
}
