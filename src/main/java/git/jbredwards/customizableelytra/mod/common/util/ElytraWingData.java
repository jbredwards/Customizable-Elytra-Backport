package git.jbredwards.customizableelytra.mod.common.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Stores the data of an elytra wing at runtime.
 * Don't modify existing instances, that may result in a desync.
 * Instead create a new instance, change that, then set to the new.
 * @author jbred
 *
 */
@Immutable
public class ElytraWingData implements INBTSerializable<NBTTagCompound>
{
    @Nonnull
    public CustomizationType type = CustomizationType.NONE;
    public int color = -1;

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Type", type.ordinal());

        if(type == CustomizationType.DYE)
            nbt.setInteger("Color", color);

        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound nbt) {
        type = CustomizationType.deserialize(nbt.getInteger("Type"));

        if(type == CustomizationType.DYE)
            color = nbt.getInteger("Color");
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        final ElytraWingData other = (ElytraWingData)o;
        if(type != other.type) return false;

        switch(type) {
            case NONE: return areGeneralPropsEqual(other);
            case DYE: return color == other.color && areGeneralPropsEqual(other);
            default:
                //TODO, banner patterns
                return true;
        }
    }

    //TODO, this will be used for stuff like glowing
    boolean areGeneralPropsEqual(@Nonnull ElytraWingData other) {
        return true;
    }
}
