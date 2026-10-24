package com.antiquark.module.gameplay;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.violetmoon.zeta.block.ZetaBlock;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.registry.IZetaBlockColorProvider;

public class ColoredWoolBlock extends ZetaBlock implements IZetaBlockColorProvider {
    public static final String COLOR_PROVIDER = "colored_wool";

    public ColoredWoolBlock(String regname, ZetaModule module, BlockBehaviour.Properties properties) {
        super(regname, module, properties);
    }

    @Override
    public String getBlockColorProviderName() {
        return COLOR_PROVIDER;
    }

    @Override
    public String getItemColorProviderName() {
        return COLOR_PROVIDER;
    }
}
