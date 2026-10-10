package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Contained End Portals", description = "End portal blocks are generated strictly inside the 3x3 frame.")
public class EndPortalPlacementModule extends ZetaModule {
    @ModuleInstance
    public static EndPortalPlacementModule INSTANCE;
}
