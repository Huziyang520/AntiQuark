package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import com.antiquark.mixin.server.EnderManTeleportHelper;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingTick;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Endermen Avoid Water Cauldrons", description = "Endermen standing in water-filled cauldrons take damage and teleport.")
public class EndermanCauldronModule extends ZetaModule {
    @ModuleInstance
    public static EndermanCauldronModule INSTANCE;

    @PlayEvent
    public void onTick(ZLivingTick event) {
        if (!(event.getEntity() instanceof EnderMan enderman) || enderman.level().isClientSide) {
            return;
        }
        BlockState below = enderman.level().getBlockState(enderman.blockPosition());
        BlockState feet = enderman.level().getBlockState(enderman.blockPosition().above());
        if (isWaterCauldron(below) || isWaterCauldron(feet)) {
            enderman.hurt(enderman.damageSources().drown(), 1.0F);
            if (enderman.isAlive()) {
                EnderManTeleportHelper.teleport(enderman);
            }
        }
    }

    private static boolean isWaterCauldron(BlockState state) {
        return state.getBlock() instanceof AbstractCauldronBlock
                && state.hasProperty(LayeredCauldronBlock.LEVEL)
                && !state.is(Blocks.POWDER_SNOW_CAULDRON);
    }
}
