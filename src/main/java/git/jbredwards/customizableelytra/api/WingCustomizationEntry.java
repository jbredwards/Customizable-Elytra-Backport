package git.jbredwards.customizableelytra.api;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author jbred
 *
 */
public final class WingCustomizationEntry
{
    @Nonnull public static final Map<String, WingCustomizationEntry> REGISTRY = new HashMap<>();

    @Nonnull public final String tagName;
    @Nonnull public final Supplier<IWingCustomization> generator;

    public WingCustomizationEntry(@Nonnull String tagNameIn, @Nonnull Supplier<IWingCustomization> generatorIn) {
        tagName = tagNameIn;
        generator = generatorIn;
    }

    @Nonnull
    public static IWingCustomization getFromTagKey(@Nonnull String key) {
        if(REGISTRY.containsKey(key))
            return REGISTRY.get(key).generator.get();

        return IWingCustomization.NONE;
    }
}
