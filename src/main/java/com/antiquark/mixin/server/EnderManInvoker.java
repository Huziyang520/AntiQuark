package com.antiquark.mixin.server;

import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnderMan.class)
public interface EnderManInvoker {
    @Invoker("teleport")
    boolean antiquark$invokeTeleport();
}
