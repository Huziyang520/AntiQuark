package com.antiquark.module.world;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.player.ZRightClickBlock;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.WORLD, name = "Nether Cauldrons Stay Dry", description = "Water cannot be placed into cauldrons in the Nether.")
public class CauldronNetherModule extends ZetaModule {
    @ModuleInstance
    public static CauldronNetherModule INSTANCE;

    @PlayEvent
    public void onRightClick(ZRightClickBlock.Low event) {
        if (!event.getLevel().dimensionType().ultraWarm()) {
            return;
        }
        BlockState state = event.getLevel().getBlockState(event.getPos());
        ItemStack held = event.getItemStack();
        if (state.getBlock() instanceof AbstractCauldronBlock
                && !state.is(Blocks.POWDER_SNOW_CAULDRON)
                && (held.getItem() == Items.WATER_BUCKET || held.getItem() == Items.POTION)) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }
}
