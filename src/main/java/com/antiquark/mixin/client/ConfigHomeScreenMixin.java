package com.antiquark.mixin.client;

import com.antiquark.AntiQuark;
import com.antiquark.client.AntiQuarkClient;
import net.minecraft.client.gui.components.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.client.config.screen.ZetaConfigHomeScreen;

/**
 * The reload notice belongs to saving this mod's config, not to every config reload.
 */
@Mixin(ZetaConfigHomeScreen.class)
public abstract class ConfigHomeScreenMixin {
    @Inject(method = "commit", at = @At("HEAD"), remap = false)
    private void antiquark$notifyConfigApplied(Button button, CallbackInfo ci) {
        Zeta zeta = ((ZetaScreenAccessor) (Object) this).antiquark$getZeta();
        if (zeta == AntiQuark.ZETA) {
            AntiQuarkClient.notifyConfigApplied();
        }
    }
}
