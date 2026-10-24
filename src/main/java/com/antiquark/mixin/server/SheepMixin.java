package com.antiquark.mixin.server;

import java.util.List;

import com.antiquark.AntiQuark;
import com.antiquark.module.gameplay.ColoredWoolModule;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public abstract class SheepMixin {
    /**
     * Player shearing goes through the Forge shearable hook, which builds its own drop list.
     */
    @Inject(method = "onSheared", at = @At("RETURN"), remap = false)
    private void antiquark$replaceShearedWool(Player player, ItemStack tool, Level level, BlockPos pos, int fortune,
            CallbackInfoReturnable<List<ItemStack>> cir) {
        Sheep sheep = (Sheep) (Object) this;
        if (!shouldDropColoredWool(sheep)) {
            return;
        }
        List<ItemStack> drops = cir.getReturnValue();
        if (drops == null) {
            return;
        }
        for (int i = 0; i < drops.size(); i++) {
            ItemStack stack = drops.get(i);
            if (stack.is(ItemTags.WOOL)) {
                drops.set(i, ColoredWoolModule.woolStack(stack.getCount()));
            }
        }
    }

    /**
     * Covers direct shear calls that skip the Forge hook.
     */
    @Inject(method = "shear", at = @At("HEAD"), cancellable = true)
    private void antiquark$dropColoredWool(SoundSource source, CallbackInfo ci) {
        Sheep sheep = (Sheep) (Object) this;
        if (!shouldDropColoredWool(sheep) || sheep.isSheared()) {
            return;
        }

        sheep.level().playSound(null, sheep, SoundEvents.SHEEP_SHEAR, source, 1.0F, 1.0F);
        sheep.setSheared(true);
        int count = 1 + sheep.getRandom().nextInt(3);
        for (int i = 0; i < count; i++) {
            ItemEntity drop = sheep.spawnAtLocation(ColoredWoolModule.woolStack(1));
            if (drop != null) {
                drop.setDeltaMovement(drop.getDeltaMovement().add(
                        (sheep.getRandom().nextFloat() - sheep.getRandom().nextFloat()) * 0.1F,
                        sheep.getRandom().nextFloat() * 0.05F,
                        (sheep.getRandom().nextFloat() - sheep.getRandom().nextFloat()) * 0.1F));
            }
        }
        ci.cancel();
    }

    private static boolean shouldDropColoredWool(Sheep sheep) {
        return !sheep.level().isClientSide
                && AntiQuark.ZETA.modules.isEnabled(ColoredWoolModule.class)
                && ColoredWoolModule.woolAvailable()
                && ColoredWoolModule.isRainbowSheep(sheep);
    }
}
