package com.antiquark.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.client.config.screen.ZetaScreen;

@Mixin(ZetaScreen.class)
public interface ZetaScreenAccessor {
    @Accessor("z")
    Zeta antiquark$getZeta();
}
