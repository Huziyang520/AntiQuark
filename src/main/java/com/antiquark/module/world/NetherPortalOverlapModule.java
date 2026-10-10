package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Overlapping Nether Portals", description = "Lighting a frame that overlaps an existing portal can create a second portal.")
public class NetherPortalOverlapModule extends ZetaModule {
    @ModuleInstance
    public static NetherPortalOverlapModule INSTANCE;
}
