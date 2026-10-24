package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.registry.CreativeTabManager;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Colored Wool", description = "Shearing a sheep named jeb_ yields wool whose color keeps shifting.")
public class ColoredWoolModule extends ZetaModule {
    @ModuleInstance
    public static ColoredWoolModule INSTANCE;

    public final Item wool = new ColoredWoolItem("colored_wool", this, new Item.Properties());

    public ColoredWoolModule() {
        CreativeTabManager.addToCreativeTab(CreativeModeTabs.INGREDIENTS, wool);
    }

    public static boolean isRainbowSheep(Sheep sheep) {
        return sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString());
    }

    public static Item wool() {
        ColoredWoolModule module = INSTANCE;
        return module == null ? Items.WHITE_WOOL : module.wool;
    }
}
