package com.antiquark;

import java.util.List;

import com.antiquark.module.CategoryKeys;
import com.antiquark.util.ConfigMigrations;
import net.minecraft.world.item.Items;
import org.violetmoon.zeta.module.ZetaCategory;
import org.violetmoon.zetaimplforge.ForgeZeta;
import org.violetmoon.zetaimplforge.module.ModFileScanDataModuleFinder;

public final class AntiQuarkZeta extends ForgeZeta {
    public static final List<ZetaCategory> CATEGORIES = List.of(
            new ZetaCategory(CategoryKeys.CREATURES, Items.CAT_SPAWN_EGG),
            new ZetaCategory(CategoryKeys.GAMEPLAY, Items.CRAFTING_TABLE)
    );

    public AntiQuarkZeta() {
        super(AntiQuark.MODID, AntiQuark.LOGGER);
    }

    public void init() {
        start();
        loadModules(CATEGORIES, new ModFileScanDataModuleFinder(AntiQuark.MODID), AntiQuarkConfig.INSTANCE);
        ConfigMigrations.apply(this);
    }
}
