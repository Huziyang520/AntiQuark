package com.antiquark.util;

import com.antiquark.AntiQuark;
import com.antiquark.module.behavior.FearlessCatModule;
import org.violetmoon.zeta.Zeta;
import org.violetmoon.zeta.config.ConfigManager;
import org.violetmoon.zeta.config.IZetaConfigInternals;
import org.violetmoon.zeta.config.SectionDefinition;
import org.violetmoon.zeta.config.ValueDefinition;
import org.violetmoon.zeta.module.ZetaModule;

public final class ConfigMigrations {
    private static final int CURRENT_VERSION = 1;
    private static final String VERSION_OPTION = "Config Version";

    private ConfigMigrations() {
    }

    public static void apply(Zeta zeta) {
        try {
            ConfigManager manager = zeta.configManager;
            IZetaConfigInternals internals = zeta.configInternals;
            if (manager == null || internals == null) {
                return;
            }

            SectionDefinition section = moduleSection(manager, FearlessCatModule.INSTANCE);
            if (section == null) {
                return;
            }
            ValueDefinition<Integer> versionOption = section.getValue(VERSION_OPTION, Integer.class);
            if (versionOption == null) {
                return;
            }
            Integer version = internals.get(versionOption);
            if (version != null && version >= CURRENT_VERSION) {
                return;
            }

            migrateFearlessCatChance(section, internals);

            internals.set(versionOption, CURRENT_VERSION);
            internals.flush();
        } catch (RuntimeException e) {
            AntiQuark.LOGGER.warn("Could not run AntiQuark config migrations", e);
        }
    }

    private static void migrateFearlessCatChance(SectionDefinition section, IZetaConfigInternals internals) {
        ValueDefinition<Double> chanceOption = section.getValue("Chance", Double.class);
        if (chanceOption == null) {
            return;
        }
        Double stored = internals.get(chanceOption);
        if (stored != null && Math.abs(stored - 0.5D) < 1.0E-6D) {
            internals.set(chanceOption, 0.3D);
        }
    }

    private static SectionDefinition moduleSection(ConfigManager manager, ZetaModule module) {
        if (module == null || module.category() == null) {
            return null;
        }
        SectionDefinition category = manager.getCategorySection(module.category());
        if (category == null) {
            return null;
        }
        for (SectionDefinition subsection : category.getSubsections()) {
            if (module.lowerCaseName().equals(subsection.name)) {
                return subsection;
            }
        }
        return null;
    }
}
