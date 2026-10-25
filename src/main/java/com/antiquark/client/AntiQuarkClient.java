package com.antiquark.client;

import java.util.List;

import com.antiquark.AntiQuark;
import com.antiquark.client.widget.CaptionWidget;
import com.antiquark.mixin.client.ZetaScreenAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.client.ZetaClient;
import org.violetmoon.zeta.client.config.screen.SectionScreen;
import org.violetmoon.zeta.client.config.screen.ZetaScreen;
import org.violetmoon.zeta.client.config.widget.CategoryButton;
import org.violetmoon.zeta.client.config.widget.CheckboxButton;
import org.violetmoon.zeta.config.ChangeSet;
import org.violetmoon.zeta.config.SectionDefinition;
import org.violetmoon.zeta.config.ValueDefinition;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zeta.module.ZetaCategory;
import org.violetmoon.zetaimplforge.client.ForgeZetaClient;

/**
 * Client-only entry point: colours, the config categories mounted into other Zeta mods' config screens,
 * and the config reload notice.
 */
public final class AntiQuarkClient {
    //Mirrors the layout constants of Zeta's config home screen
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ROW_HEIGHT = 23;
    private static final int BUTTONS_PER_ROW = 3;
    private static final int H_PAD = 10;
    private static final int FIRST_ROW_Y = 70;
    private static final int HEADER_COLOR = 0x48ddbc;

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

    public static void notifyConfigApplied() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gui == null) {
            return;
        }
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded0").withStyle(ChatFormatting.AQUA));
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded1").withStyle(ChatFormatting.WHITE));
        minecraft.gui.getChat().addMessage(Component.translatable("antiquark.misc.reloaded2").withStyle(ChatFormatting.WHITE));
    }

    /**
     * Mounts this mod's config categories under the categories of other Zeta mods' config screens,
     * with a header matching the one those screens draw.
     */
    private static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof ZetaScreen zetaScreen)) {
            return;
        }
        Zeta foreign = ((ZetaScreenAccessor) zetaScreen).antiquark$getZeta();
        Zeta ours = AntiQuark.ZETA;
        if (foreign == ours || ours.modules == null || ours.configManager == null || ours.configInternals == null) {
            return;
        }

        List<ZetaCategory> categories = ours.modules.getInhabitedCategories();
        SectionDefinition general = ours.configManager.getGeneralSection();
        int count = categories.size() + (general == null ? 0 : 1);
        if (count == 0) {
            return;
        }

        int foreignButtons = foreign.modules.getInhabitedCategories().size()
                + (foreign.configManager.getGeneralSection() == null ? 0 : 1);
        int foreignRows = Math.max(1, (foreignButtons + BUTTONS_PER_ROW - 1) / BUTTONS_PER_ROW);
        int titleY = FIRST_ROW_Y + foreignRows * ROW_HEIGHT + 4;
        int gridY = titleY + 12;

        event.addListener(new CaptionWidget(0, titleY, screen.width,
                Component.translatable("quark.gui.config.header", AntiQuark.NAME).withStyle(ChatFormatting.BOLD), HEADER_COLOR));

        ChangeSet changeSet = new ApplyingChangeSet(ours.configInternals);

        for (int index = 0; index < count; index++) {
            int row = index / BUTTONS_PER_ROW;
            int perRow = Math.min(BUTTONS_PER_ROW, count - row * BUTTONS_PER_ROW);
            int x = startX(screen.width, perRow) + (index % BUTTONS_PER_ROW) * (BUTTON_WIDTH + H_PAD);
            int y = gridY + row * ROW_HEIGHT;

            if (index < categories.size()) {
                ZetaCategory category = categories.get(index);
                SectionDefinition section = ours.configManager.getCategorySection(category);
                ValueDefinition<Boolean> enabled = ours.configManager.getCategoryEnabledOption(category);
                if (section == null || enabled == null) {
                    continue;
                }
                int mainWidth = BUTTON_WIDTH - 20;
                event.addListener(new CategoryButton(x, y, mainWidth, BUTTON_HEIGHT,
                        Component.translatable("antiquark.category." + section.name), category.icon.get(),
                        button -> Minecraft.getInstance().setScreen(new SectionScreen(zetaClient(), screen, changeSet, section))));
                event.addListener(new CheckboxButton(zetaClient(), x + mainWidth, y, changeSet, enabled));
            } else if (general != null) {
                event.addListener(Button.builder(Component.translatable("antiquark.category." + general.name),
                                button -> Minecraft.getInstance().setScreen(new SectionScreen(zetaClient(), screen, changeSet, general)))
                        .bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                        .build());
            }
        }
    }

    private static int startX(int centerX, int count) {
        int totalWidth = count * BUTTON_WIDTH + Math.max(0, count - 1) * H_PAD;
        return (centerX - totalWidth) / 2;
    }
}
