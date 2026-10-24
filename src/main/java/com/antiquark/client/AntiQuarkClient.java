package com.antiquark.client;

import com.antiquark.AntiQuark;
import com.antiquark.mixin.client.ZetaScreenAccessor;
import com.antiquark.module.gameplay.ColoredWoolBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.client.ZetaClient;
import org.violetmoon.zeta.client.config.screen.ZetaConfigHomeScreen;
import org.violetmoon.zeta.client.config.screen.ZetaScreen;
import org.violetmoon.zeta.client.event.load.ZAddBlockColorHandlers;
import org.violetmoon.zeta.client.event.load.ZAddItemColorHandlers;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zeta.event.load.ZConfigChanged;
import org.violetmoon.zetaimplforge.client.ForgeZetaClient;

/**
 * Client-only entry point: item and block colours, config screen hooks and the config reload notice.
 */
public final class AntiQuarkClient {
    private static ZetaClient zetaClient;

    private AntiQuarkClient() {
    }

    public static void init() {
        //Created eagerly so every client load event reaches Zeta's own client extensions
        zetaClient = new ForgeZetaClient(AntiQuark.ZETA);
        AntiQuark.ZETA.loadBus.subscribe(AntiQuarkClient.class);
        MinecraftForge.EVENT_BUS.addListener(AntiQuarkClient::onScreenInit);
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
        event.registerNamed(AntiQuark.ZETA, item -> rainbow, ColoredWoolBlock.COLOR_PROVIDER);
    }

    @LoadEvent
    public static void registerBlockColors(ZAddBlockColorHandlers event) {
        BlockColor rainbow = (state, level, pos, tintIndex) -> tintIndex == 0 ? RainbowColors.currentColor() : -1;
        event.registerNamed(AntiQuark.ZETA, block -> rainbow, ColoredWoolBlock.COLOR_PROVIDER);
    }

    @LoadEvent
    public static void onConfigChanged(ZConfigChanged event) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            if (minecraft.gui == null) {
                return;
            }
            minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded0").withStyle(ChatFormatting.AQUA));
            minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded1").withStyle(ChatFormatting.WHITE));
            minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded2").withStyle(ChatFormatting.WHITE));
        });
    }

    //Adds a link to this mod's config in other Zeta mods' config screens
    private static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof ZetaScreen zetaScreen)) {
            return;
        }
        Zeta zeta = ((ZetaScreenAccessor) zetaScreen).antiquark$getZeta();
        if (zeta == AntiQuark.ZETA) {
            return;
        }
        event.addListener(Button.builder(Component.translatable("antiquark.gui.config.open"),
                        button -> Minecraft.getInstance().setScreen(new ZetaConfigHomeScreen(zetaClient(), screen)))
                .bounds(6, screen.height - 26, 110, 20)
                .build());
    }
}
