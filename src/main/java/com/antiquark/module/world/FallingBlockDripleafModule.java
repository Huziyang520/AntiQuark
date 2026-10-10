package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Falling Blocks Break Dripleaves", description = "Falling gravity blocks break big dripleaves and keep falling.")
public class FallingBlockDripleafModule extends ZetaModule {
    @ModuleInstance
    public static FallingBlockDripleafModule INSTANCE;
}
