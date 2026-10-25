package com.antiquark;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AntiQuark.MODID)
public final class AntiQuark {
    public static final String MODID = "antiquark";
    public static final String NAME = "AntiQuark";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final AntiQuarkZeta ZETA = new AntiQuarkZeta();

    public AntiQuark() {
        ZETA.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            com.antiquark.client.AntiQuarkClient.init();
        }
    }
}
