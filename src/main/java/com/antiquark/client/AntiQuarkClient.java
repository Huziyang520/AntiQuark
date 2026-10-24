package com.antiquark.client;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.ColoredWoolItem;
import net.minecraft.client.color.item.ItemColor;
import org.violetmoon.zeta.client.ZetaClient;
import org.violetmoon.zeta.client.event.load.ZAddItemColorHandlers;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zetaimplforge.client.ForgeZetaClient;

/**
 * Client-only entry point: item colours plus the shared {@link ZetaClient} used by the config screen.
 */
public final class AntiQuarkClient {
    private static ZetaClient zetaClient;

    private AntiQuarkClient() {
    }

    public static void init() {
        //Created eagerly so every client load event reaches Zeta's own client extensions
        zetaClient = new ForgeZetaClient(AntiQuark.ZETA);
        AntiQuark.ZETA.loadBus.subscribe(AntiQuarkClient.class);
    }

    public static ZetaClient zetaClient() {
        if (zetaClient == null) {
            init();
        }
        return zetaClient;
    }

    @LoadEvent
    public static void registerItemColors(ZAddItemColorHandlers event) {
        ItemColor rainbow = (stack, tintIndex) -> tintIndex == 0 ? RainbowColors.currentColor() : -1;
        event.registerNamed(AntiQuark.ZETA, item -> rainbow, ColoredWoolItem.COLOR_PROVIDER);
    }
}
