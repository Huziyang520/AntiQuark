package com.antiquark.client;

import com.antiquark.AntiQuark;
import com.antiquark.mixin.client.ZetaScreenAccessor;
import com.antiquark.module.gameplay.ColoredWoolBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.client.ZetaClient;
import org.violetmoon.zeta.client.config.screen.ZetaConfigHomeScreen;
import org.violetmoon.zeta.client.config.screen.ZetaScreen;
import org.violetmoon.zeta.client.config.widget.CategoryButton;
import org.violetmoon.zeta.client.event.load.ZAddBlockColorHandlers;
import org.violetmoon.zeta.client.event.load.ZAddItemColorHandlers;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zetaimplforge.client.ForgeZetaClient;

/**
 * Client-only entry point: item and block colours, the config screen entry and the reload notice.
 */
public final class AntiQuarkClient {
    //Matches the layout constants of Zeta's config home screen
    private static final int BUTTON_WIDTH = 120;
    private static final int FIRST_ROW_Y = 70;
    private static final int ROW_HEIGHT = 23;
    private static final int BUTTONS_PER_ROW = 3;

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

    public static void notifyConfigApplied() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui == null) {
            return;
        }
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded0").withStyle(ChatFormatting.AQUA));
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded1").withStyle(ChatFormatting.WHITE));
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded2").withStyle(ChatFormatting.WHITE));
    }

    //Adds this mod's entry below the category buttons of other Zeta mods' config screens
    private static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof ZetaScreen zetaScreen)) {
            return;
        }
        Zeta zeta = ((ZetaScreenAccessor) zetaScreen).antiquark$getZeta();
        if (zeta == AntiQuark.ZETA || zeta.modules == null || zeta.configManager == null) {
            return;
        }

        int buttons = zeta.modules.getInhabitedCategories().size();
        if (zeta.configManager.getGeneralSection() != null) {
            buttons++;
        }
        int rows = Math.max(1, (buttons + BUTTONS_PER_ROW - 1) / BUTTONS_PER_ROW);
        int y = FIRST_ROW_Y + rows * ROW_HEIGHT + 2;
        int x = (screen.width - BUTTON_WIDTH) / 2;

        event.addListener(new CategoryButton(x, y, BUTTON_WIDTH, 20,
                Component.translatable("antiquark.gui.config.open"), new ItemStack(Items.AMETHYST_SHARD),
                button -> Minecraft.getInstance().setScreen(new ZetaConfigHomeScreen(zetaClient(), screen))));
    }
}
