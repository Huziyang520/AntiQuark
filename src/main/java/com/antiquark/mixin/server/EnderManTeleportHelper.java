package com.antiquark.mixin.server;

import net.minecraft.world.entity.monster.EnderMan;

public final class EnderManTeleportHelper {
    private EnderManTeleportHelper() {
    }

    public static boolean teleport(EnderMan enderman) {
        return ((EnderManInvoker) enderman).antiquark$invokeTeleport();
    }
}
