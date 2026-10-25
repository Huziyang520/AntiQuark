package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zeta.event.load.ZRegister;
import org.violetmoon.zeta.item.ZetaItem;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.registry.CreativeTabManager;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Flour", description = "Wheat can be ground into flour, and flour can be turned into slime balls.")
public class FlourModule extends ZetaModule {
    @ModuleInstance
    public static FlourModule INSTANCE;

    private Item flour;

    @LoadEvent
    public void register(ZRegister event) {
        flour = new ZetaItem("flour", this, new Item.Properties());
        CreativeTabManager.addToTab(CreativeModeTabs.INGREDIENTS, flour);
    }
}
