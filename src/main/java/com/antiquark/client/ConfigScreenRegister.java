package com.antiquark.client;

import com.antiquark.AntiQuark;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.violetmoon.zeta.client.config.screen.ZetaConfigHomeScreen;

@Mod.EventBusSubscriber(modid = AntiQuark.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ConfigScreenRegister {
    private ConfigScreenRegister() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) ->
                        new ZetaConfigHomeScreen(AntiQuarkClient.zetaClient(), parent)));
    }
}
