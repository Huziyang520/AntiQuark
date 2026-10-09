package com.antiquark.module;

import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = "antiquark")
public class CoreModule extends ZetaModule {
    @ModuleInstance
    public static CoreModule INSTANCE;
}
