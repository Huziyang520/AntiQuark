package com.antiquark.module.gameplay;

import net.minecraft.world.item.Item;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.registry.IZetaItemColorProvider;
import org.violetmoon.zeta.item.ZetaItem;

public class ColoredWoolItem extends ZetaItem implements IZetaItemColorProvider {
    public static final String COLOR_PROVIDER = "colored_wool";

    public ColoredWoolItem(String regname, ZetaModule module, Item.Properties properties) {
        super(regname, module, properties);
    }

    @Override
    public String getItemColorProviderName() {
        return COLOR_PROVIDER;
    }
}
