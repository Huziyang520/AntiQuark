package com.antiquark.module.behavior;

import com.antiquark.AntiQuark;
import org.violetmoon.zeta.module.ZetaModule;

public final class AntiQuarkZetaHelper {
    private AntiQuarkZetaHelper() {
    }

    public static boolean disabled(Class<? extends ZetaModule> type) {
        return !AntiQuark.ZETA.modules.isEnabled(type);
    }
}
