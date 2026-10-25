package com.antiquark.client;

import com.antiquark.AntiQuark;
import org.violetmoon.zeta.config.ChangeSet;
import org.violetmoon.zeta.config.IZetaConfigInternals;
import org.violetmoon.zeta.config.ValueDefinition;

public final class ApplyingChangeSet extends ChangeSet {
    public ApplyingChangeSet(IZetaConfigInternals internals) {
        super(internals);
    }

    @Override
    public <T> void set(ValueDefinition<T> valueDef, T nextValue) {
        super.set(valueDef, nextValue);
        applyAllChanges();
    }
}
