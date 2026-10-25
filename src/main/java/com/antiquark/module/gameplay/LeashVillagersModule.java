package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, enabledByDefault = false, name = "Leash Villagers", description = "Villagers can be leashed without the leash-any-mob feature.")
public class LeashVillagersModule extends ZetaModule {
    @ModuleInstance
    public static LeashVillagersModule INSTANCE;
}
