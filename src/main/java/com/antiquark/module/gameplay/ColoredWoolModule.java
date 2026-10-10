package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.item.ItemEntity;
import org.violetmoon.zeta.event.bus.PlayEvent;
import org.violetmoon.zeta.event.play.entity.living.ZLivingDrops;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Rainbow Wool", description = "Shearing a jeb_ sheep drops wool that cycles through every color.")
public class ColoredWoolModule extends ZetaModule {
    @ModuleInstance
    public static ColoredWoolModule INSTANCE;

    @PlayEvent
    public void onDrops(ZLivingDrops event) {
        if (event.getEntity() instanceof net.minecraft.world.entity.animal.Sheep sheep
                && sheep.hasCustomName()
                && "jeb_".equals(sheep.getName().getString())
                && !sheep.isSheared()
                && !sheep.level().isClientSide) {
            DyeColor color = DyeColor.byId(sheep.level().random.nextInt(16));
            ItemStack wool = new ItemStack(woolItem(color));
            event.getDrops().add(new ItemEntity(sheep.level(), sheep.getX(), sheep.getY(), sheep.getZ(), wool));
        }
    }

    private static net.minecraft.world.item.Item woolItem(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_WOOL;
            case ORANGE -> Items.ORANGE_WOOL;
            case MAGENTA -> Items.MAGENTA_WOOL;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
            case YELLOW -> Items.YELLOW_WOOL;
            case LIME -> Items.LIME_WOOL;
            case PINK -> Items.PINK_WOOL;
            case GRAY -> Items.GRAY_WOOL;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
            case CYAN -> Items.CYAN_WOOL;
            case PURPLE -> Items.PURPLE_WOOL;
            case BLUE -> Items.BLUE_WOOL;
            case BROWN -> Items.BROWN_WOOL;
            case GREEN -> Items.GREEN_WOOL;
            case RED -> Items.RED_WOOL;
            case BLACK -> Items.BLACK_WOOL;
        };
    }
}
