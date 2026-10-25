package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Grass to Green Dye", description = "Nine of any grass can be crafted into two green dye.")
public class GrassDyeModule extends ZetaModule {
    @ModuleInstance
    public static GrassDyeModule INSTANCE;
}
