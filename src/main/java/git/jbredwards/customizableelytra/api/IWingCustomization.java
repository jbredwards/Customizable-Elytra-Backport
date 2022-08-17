package git.jbredwards.customizableelytra.api;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
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
    @Nonnull
    IWingCustomization NONE = new IWingCustomization() {
        public boolean isValid(@Nonnull WingCustomizationData data) { return false; }

        @SideOnly(Side.CLIENT)
        public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) { }
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

    /**
     * Called whenever a customization is removed, including when other customizations are removed
     */
    default void onDataRemoved(@Nonnull WingCustomizationData data, boolean removed) { }

    default void writeToNBT(@Nonnull NBTTagCompound compound) {}
    default void readFromNBT(@Nonnull NBTTagCompound compound) {}

    //===============================
    //client-only rendering utilities
    //===============================

    @SideOnly(Side.CLIENT)
    void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced);

    @Nonnull
    @SideOnly(Side.CLIENT)
    default EnumActionResult changeEquipmentColor(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing) {
        return EnumActionResult.PASS;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    default EnumActionResult changeTexture(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, @Nonnull Consumer<ResourceLocation> textureBinder) {
        return EnumActionResult.PASS;
    }

    @SideOnly(Side.CLIENT)
    default void preRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}

    @Nonnull
    @SideOnly(Side.CLIENT)
    default EnumActionResult renderModel(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        return EnumActionResult.PASS;
    }

    @SideOnly(Side.CLIENT)
    default void postRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}
}
