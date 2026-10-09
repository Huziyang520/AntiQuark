package com.antiquark;

import org.violetmoon.zeta.config.Config;

public class AntiQuarkConfig {
    public static final AntiQuarkConfig INSTANCE = new AntiQuarkConfig();

    @Config(name = "Use Anti Overlap", description = "Disable modules that overlap with other installed mods.")
    public boolean useAntiOverlap = true;
}
