package com.antiquark.module.behavior;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.item.Items;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;

@ZetaLoadModule(category = CategoryKeys.CREATURES, name = "Wolf Targets Skeleton Horses", description = "Wolves consider skeleton horses valid prey.")
public class WolfTargetsSkeletonHorseModule extends ZetaModule {
    @ModuleInstance
    public static WolfTargetsSkeletonHorseModule INSTANCE;
}
