package com.antiquark.client;

import net.minecraft.util.Mth;

/**
 * Cycles a hue over time so tinted items shimmer like a jeb_ sheep.
 */
public final class RainbowColors {
    private static final long PERIOD_MILLIS = 4000L;
    private static final float SATURATION = 0.75F;
    private static final float BRIGHTNESS = 1.0F;

    private RainbowColors() {
    }

    public static int currentColor() {
        float hue = (float) (System.currentTimeMillis() % PERIOD_MILLIS) / (float) PERIOD_MILLIS;
        return Mth.hsvToRgb(hue, SATURATION, BRIGHTNESS) & 0xFFFFFF;
    }
}
