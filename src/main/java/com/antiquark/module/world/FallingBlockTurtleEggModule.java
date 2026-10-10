package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Falling Blocks Break Turtle Eggs", description = "Falling gravity blocks crush turtle eggs.")
public class FallingBlockTurtleEggModule extends ZetaModule {
    @ModuleInstance
    public static FallingBlockTurtleEggModule INSTANCE;
}
