package git.jbredwards.customizableelytra.mod.common.util;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public enum CustomizationType implements IStringSerializable
{
    NONE("none"),
    DYE("dye"),
    BANNER("banner");

    @Nonnull final String name;
    CustomizationType(@Nonnull String name) { this.name = name; }

    @Nonnull
    @Override
    public String getName() { return name; }

    @Nonnull
    public static CustomizationType deserialize(int serialized) { return values()[serialized % values().length]; }
}
