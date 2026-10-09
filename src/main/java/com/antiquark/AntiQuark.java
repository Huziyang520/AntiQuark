package com.antiquark;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AntiQuark.MODID)
public final class AntiQuark {
    public static final String MODID = "antiquark";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final AntiQuarkZeta ZETA = new AntiQuarkZeta();

    public AntiQuark(IEventBus bus) {
        ZETA.init();
    }
}
