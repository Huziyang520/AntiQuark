package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.animal.Sheep;
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
        if (event.getEntity() instanceof Sheep sheep
                && isRainbowSheep(sheep)
                && !sheep.isSheared()
                && !sheep.level().isClientSide) {
            for (ItemEntity drop : event.getDrops()) {
                ItemStack stack = drop.getItem();
                if (isWool(stack.getItem())) {
                    drop.setItem(new ItemStack(randomWoolItem(sheep), stack.getCount()));
                }
            }
        }
    }

    public static boolean isRainbowSheep(Sheep sheep) {
        return sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString());
    }

    public static Item randomWoolItem(Sheep sheep) {
        return woolItem(DyeColor.byId(sheep.getRandom().nextInt(16)));
    }

    private static boolean isWool(Item item) {
        return item == Items.WHITE_WOOL || item == Items.ORANGE_WOOL || item == Items.MAGENTA_WOOL
                || item == Items.LIGHT_BLUE_WOOL || item == Items.YELLOW_WOOL || item == Items.LIME_WOOL
                || item == Items.PINK_WOOL || item == Items.GRAY_WOOL || item == Items.LIGHT_GRAY_WOOL
                || item == Items.CYAN_WOOL || item == Items.PURPLE_WOOL || item == Items.BLUE_WOOL
                || item == Items.BROWN_WOOL || item == Items.GREEN_WOOL || item == Items.RED_WOOL
                || item == Items.BLACK_WOOL;
    }

    private static Item woolItem(DyeColor color) {
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
