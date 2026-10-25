package com.antiquark.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

public final class VoidTotemHelper {
    private static final int HORIZONTAL_RANGE = 128;
    private static final int COLUMN_ATTEMPTS = 24;

    private VoidTotemHelper() {
    }

    public static boolean tryVoidTotem(LivingEntity entity, DamageSource source) {
        if (entity.level().isClientSide) {
            return false;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = entity.getItemInHand(hand);
            if (stack.isEmpty() || !stack.is(Items.TOTEM_OF_UNDYING)) {
                continue;
            }
            BlockPos landing = findLanding(entity);
            if (landing == null) {
                return false;
            }
            if (!ForgeHooks.onLivingUseTotem(entity, source, stack, hand)) {
                return false;
            }
            stack.shrink(1);
            applyTotem(entity, landing);
            return true;
        }
        return false;
    }

    private static void applyTotem(LivingEntity entity, BlockPos landing) {
        entity.setHealth(1.0F);
        entity.removeAllEffects();
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        entity.teleportTo(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D);
        entity.setDeltaMovement(Vec3.ZERO);
        entity.resetFallDistance();

        entity.level().broadcastEntityEvent(entity, (byte) 35);
    }

    private static BlockPos findLanding(LivingEntity entity) {
        Level level = entity.level();
        BlockPos origin = entity.blockPosition();
        int top = level.getMaxBuildHeight() - 2;
        int bottom = level.getMinBuildHeight() + 1;

        for (int attempt = 0; attempt < COLUMN_ATTEMPTS; attempt++) {
            int x = origin.getX() + entity.getRandom().nextInt(HORIZONTAL_RANGE * 2 + 1) - HORIZONTAL_RANGE;
            int z = origin.getZ() + entity.getRandom().nextInt(HORIZONTAL_RANGE * 2 + 1) - HORIZONTAL_RANGE;
            if (!level.hasChunkAt(x, z)) {
                continue;
            }
            for (int y = top; y > bottom; y--) {
                BlockPos landing = new BlockPos(x, y, z);
                BlockState state = level.getBlockState(landing);
                if (!state.getFluidState().isEmpty() || !state.getCollisionShape(level, landing).isEmpty()) {
                    continue;
                }
                BlockPos ground = landing.below();
                BlockState groundState = level.getBlockState(ground);
                if (!groundState.getFluidState().isEmpty() || groundState.getCollisionShape(level, ground).isEmpty()) {
                    continue;
                }
                AABB box = entity.getType().getDimensions()
                        .makeBoundingBox(landing.getX() + 0.5D, landing.getY(), landing.getZ() + 0.5D);
                if (level.noCollision(entity, box)) {
                    return landing;
                }
            }
        }
        return null;
    }
}
