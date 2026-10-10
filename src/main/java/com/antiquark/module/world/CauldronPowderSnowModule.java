package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingTick;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Powder Snow Cauldrons Hurt", description = "Standing inside a powder snow cauldron causes freezing damage.")
public class CauldronPowderSnowModule extends ZetaModule {
    @ModuleInstance
    public static CauldronPowderSnowModule INSTANCE;

    @PlayEvent
    public void onTick(ZLivingTick event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }
        BlockPos pos = entity.blockPosition();
        BlockState state = entity.level().getBlockState(pos);
        if (state.getBlock() instanceof PowderSnowCauldronBlock) {
            entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 2, 140));
        }
    }
}
