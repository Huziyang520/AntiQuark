package com.antiquark.util;

import net.minecraft.world.entity.monster.EnderMan;

public final class EnderManTeleportHelper {
    private static final int ATTEMPTS = 64;

    private EnderManTeleportHelper() {
    }

    public static boolean teleport(EnderMan enderman) {
        if (enderman.level().isClientSide || !enderman.isAlive()) {
            return false;
        }
        if (enderman.isPassenger()) {
            enderman.stopRiding();
        }
        for (int attempt = 0; attempt < ATTEMPTS; attempt++) {
            double x = enderman.getX() + (enderman.getRandom().nextDouble() - 0.5D) * 64.0D;
            double y = enderman.getY() + (enderman.getRandom().nextInt(64) - 32);
            double z = enderman.getZ() + (enderman.getRandom().nextDouble() - 0.5D) * 64.0D;
            if (enderman.randomTeleport(x, y, z, true)) {
                return true;
            }
        }
        return false;
    }
}
