package com.antiquark.module.gameplay;

import com.antiquark.module.CategoryKeys;

import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.violetmoon.zeta.block.ZetaBlock;
import org.violetmoon.zeta.event.bus.LoadEvent;
import org.violetmoon.zeta.event.load.ZRegister;
import org.violetmoon.zeta.module.ModuleInstance;
import org.violetmoon.zeta.module.ZetaLoadModule;
import org.violetmoon.zeta.module.ZetaModule;
import org.violetmoon.zeta.registry.CreativeTabManager;

@ZetaLoadModule(category = CategoryKeys.GAMEPLAY, name = "Colored Wool", description = "Shearing a sheep named jeb_ yields wool whose color keeps shifting.")
public class ColoredWoolModule extends ZetaModule {
    @ModuleInstance
    public static ColoredWoolModule INSTANCE;

    private Block wool;

    //The block registry is frozen during mod construction, so content is created at registry time
    @LoadEvent
    public void register(ZRegister event) {
        wool = new ZetaBlock("colored_wool", this, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL));
        CreativeTabManager.addToCreativeTab(CreativeModeTabs.BUILDING_BLOCKS, wool);
    }

    public static boolean isRainbowSheep(Sheep sheep) {
        return sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString());
    }

    public static ItemStack woolStack(int count) {
        ColoredWoolModule module = INSTANCE;
        if (module == null || module.wool == null) {
            return new ItemStack(Items.WHITE_WOOL, count);
        }
        return new ItemStack(module.wool, count);
    }

    public static boolean woolAvailable() {
        ColoredWoolModule module = INSTANCE;
        return module != null && module.wool != null;
    }
}
