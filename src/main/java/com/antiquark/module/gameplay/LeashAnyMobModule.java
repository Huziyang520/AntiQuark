package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Leash Any Mob", description = "Leashes can attach to any mob in survival mode.")
public class LeashAnyMobModule extends ZetaModule {
    @ModuleInstance
    public static LeashAnyMobModule INSTANCE;
}
