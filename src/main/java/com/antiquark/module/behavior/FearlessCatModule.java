package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.config.Config;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Fearless Cats", description = "Some naturally spawned cats no longer flee from players.")
public class FearlessCatModule extends ZetaModule {
    @ModuleInstance
    public static FearlessCatModule INSTANCE;

    @Config(name = "Chance", description = "Chance for a newly spawned cat to be fearless.")
    public double chance = 0.5D;

    public static double chance() {
        return INSTANCE == null ? 0.5D : INSTANCE.chance;
    }
}
