package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Guaranteed Zombie Villagers", description = "Zombies always convert villagers on any non-peaceful difficulty.")
public class ZombieVillagerConversionModule extends ZetaModule {
    @ModuleInstance
    public static ZombieVillagerConversionModule INSTANCE;
}
