package git.jbredwards.customizableelytra.api;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author jbred
 *
 */
public interface IWingCustomization
{
    /**
     * Always used instead of a null value
     */
    @Nonnull IWingCustomization NONE = new IWingCustomization() {
        public boolean isValid(@Nonnull WingCustomizationData data) { return false; }
    };

    /**
     * Called when this is washed with a cauldron, the data passed in is mutable and has yet to be assigned to the capability
     */
    default void onWash(@Nonnull WingCustomizationData data) { data.removeLast(); }

    /**
     * If a customization is invalid, it can't influence rendering,  display its tooltip, or be saved/loaded
     */
    default boolean isValid(@Nonnull WingCustomizationData data) { return true; }

    /**
     * Called when this is applied to an elytra wing, the data passed in is mutable and has yet to be assigned to the capability
     * TODO, banner customizations will use this to change baseColor
     */
    default void onAddedToData(@Nonnull WingCustomizationData data) { }

    default void writeToNBT(@Nonnull NBTTagCompound compound) {}
    default void readFromNBT(@Nonnull NBTTagCompound compound) {}

    //===============================
    //client-only rendering utilities
    //===============================

    @SideOnly(Side.CLIENT)
    default void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {}

    @Nonnull
    @SideOnly(Side.CLIENT)
    default EnumActionResult changeEquipmentColor(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull AbstractClientPlayer entity) {
        return EnumActionResult.PASS;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    default EnumActionResult changeTexture(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull AbstractClientPlayer entity, @Nonnull Consumer<ResourceLocation> textureBinder) {
        return EnumActionResult.PASS;
    }
}
