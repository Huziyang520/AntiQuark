package com.antiquark.mixin.server;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class VoidTotemHelper {
    private VoidTotemHelper() {
    }

    public static boolean tryVoidTotem(LivingEntity entity) {
        if (entity.level().isClientSide) {
            return false;
        }
        for (ItemStack stack : entity.getHandSlots()) {
            if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                stack.shrink(1);
                entity.setHealth(entity.getMaxHealth());
                entity.removeAllEffects();
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                BlockPos safe = findSafe(entity);
                entity.teleportTo(safe.getX() + 0.5D, safe.getY(), safe.getZ() + 0.5D);
                return true;
            }
        }
        return false;
    }

    private static BlockPos findSafe(LivingEntity entity) {
        BlockPos pos = entity.blockPosition();
        BlockPos.MutableBlockPos mutable = pos.mutable();
        mutable.setY(Math.max(entity.level().getMinBuildHeight() + 64, pos.getY()));
        while (mutable.getY() < entity.level().getMaxBuildHeight() - 1) {
            mutable.move(0, 1, 0);
            if (!entity.level().getBlockState(mutable).isAir() && entity.level().getBlockState(mutable.above()).isAir()) {
                break;
            }
        }
        return mutable.immutable();
    }
}
