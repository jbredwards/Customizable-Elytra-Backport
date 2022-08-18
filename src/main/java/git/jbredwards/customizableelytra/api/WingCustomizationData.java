package git.jbredwards.customizableelytra.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Stores the data of an elytra wing at runtime.
 * Don't modify existing instances, that may result in a desync.
 * Instead create a new instance, change that, then set to the new.
 * @author jbred
 *
 */
@Immutable
public final class WingCustomizationData implements INBTSerializable<NBTTagCompound>
{
    //Cannot contain duplicate tags
    @Nonnull
    List<String> tags = new ArrayList<>();

    //Each customization corresponds to a tag in the tags list
    @Nonnull
    List<IWingCustomization> customizations = new ArrayList<>();

    //Used for coloring the item
    public int baseColor = -1;

    /**
     * Creates a copy of the original, useful for changing bits of data without causing desync issues
     */
    @Nonnull
    public static WingCustomizationData copyOf(@Nonnull WingCustomizationData original) {
        final WingCustomizationData copy = new WingCustomizationData();
        copy.deserializeNBT(original.serializeNBT());
        return copy;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("BaseColor", baseColor);

        final NBTTagCompound customizationData = new NBTTagCompound();
        final NBTTagList customizationTags = new NBTTagList();

        for(int i = 0; i < size(); i ++) {
            final IWingCustomization customization = getCustomizationAt(i);
            if(customization.isValid(this)) {
                customization.writeToNBT(customizationData);
                customizationTags.appendTag(new NBTTagString(getTagAt(i)));
            }
        }

        nbt.setTag("CustomizationData", customizationData);
        nbt.setTag("CustomizationTags", customizationTags);
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound nbt) {
        baseColor = nbt.getInteger("BaseColor");

        final NBTTagCompound customizationData = nbt.getCompoundTag("CustomizationData");
        final NBTTagList customizationTags = nbt.getTagList("CustomizationTags", Constants.NBT.TAG_STRING);

        for(int i = 0; i < customizationTags.tagCount(); i++) {
            final String key = customizationTags.getStringTagAt(i);
            final IWingCustomization customization = WingCustomizationEntry.getFromTagKey(key);
            if(customization != IWingCustomization.NONE) {
                customization.readFromNBT(customizationData);
                addCustomization(key, customization);
            }
        }
    }

    public boolean hasTag(@Nonnull String tag) { return tags.contains(tag); }

    @Nonnull
    public String getTagAt(int index) { return tags.get(index); }

    @Nonnull
    public IWingCustomization getCustomizationAt(int index) {
        if(customizations.isEmpty() || customizations.size() <= index)
            return IWingCustomization.NONE;

        return customizations.get(index);
    }

    @Nonnull
    public IWingCustomization getLastCustomization() { return getCustomizationAt(size() - 1); }

    /**
     * @return true if the customization is safe to add
     */
    public boolean isCompatible(@Nonnull WingCustomizationEntry customization) {
        return isCompatible(customization.generator.get());
    }

    /**
     * @return true if the customization is safe to add
     */
    public boolean isCompatible(@Nonnull IWingCustomization customization) {
        for(IWingCustomization other : customizations) {
            if(customization.isConflictingWith(this, other) || other.isConflictingWith(this, customization))
                return false;
        }

        return true;
    }

    /**
     * Should only be called during construction
     */
    public boolean addCustomization(@Nonnull String key, @Nonnull IWingCustomization customization) {
        if(customization.isValid(this) && isCompatible(customization)) {
            if(tags.contains(key)) remove(tags.indexOf(key));
            customizations.add(customization);
            tags.add(key);

            customization.onAddedToData(this);
            return true;
        }

        return false;
    }

    /**
     * Adds an empty instance of the customization entry, should only be called during construction
     */
    public boolean addCustomization(@Nonnull WingCustomizationEntry entry) {
        return addCustomization(entry.tagName, entry.generator.get());
    }

    /**
     * Should only be called during construction
     */
    public void remove(int index) {
        for(int i = size() - 1; i >= 0; i--) {
            final IWingCustomization customization = getCustomizationAt(i);
            if(customization.isValid(this)) customization.onDataRemoved(this, i == index);
        }

        customizations.remove(index);
        tags.remove(index);
    }

    /**
     * Removes the most recent customization, used when elytra are washed, should only be called during construction
     */
    public void removeLast() { remove(size() - 1); }

    public int size() { return tags.size(); }

    public void forEach(@Nonnull Consumer<IWingCustomization> action) { customizations.forEach(action); }
}
