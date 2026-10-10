package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Void Totem", description = "Held totems of undying save you from the void and teleport you upward.")
public class VoidTotemModule extends ZetaModule {
    @ModuleInstance
    public static VoidTotemModule INSTANCE;
}
