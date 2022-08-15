package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class DyeWingCustomization implements IWingCustomization
{
    public int dyeColor;
    boolean valid = true;

    public DyeWingCustomization() { this(-1); }
    public DyeWingCustomization(int color) { dyeColor = color; }

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {
        if(advanced) tooltip.add(I18n.format("item.color", String.format("#%06X", dyeColor)));
        else tooltip.add(TextFormatting.ITALIC + I18n.format("item.dyed"));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public EnumActionResult changeEquipmentColor(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull AbstractClientPlayer entity, @Nonnull EnumHandSide wing) {
        final float[] rgb = new Color(dyeColor).getRGBColorComponents(new float[3]);
        GlStateManager.color(rgb[0], rgb[1], rgb[2]);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        if(compound.hasKey("DyeColor", Constants.NBT.TAG_INT))
            dyeColor = compound.getInteger("DyeColor");
    }

    @Override
    public void writeToNBT(@Nonnull NBTTagCompound compound) { compound.setInteger("DyeColor", dyeColor); }

    @Override
    public void onAddedToData(@Nonnull WingCustomizationData data) {
        //sync colors if one is empty
        if(data.baseColor == -1) data.baseColor = dyeColor;
        else if(dyeColor == -1) dyeColor = data.baseColor;
        //customization is doing nothing, this is bad and won't be saved
        if(dyeColor == -1) valid = false;
    }

    @Override
    public boolean isValid(@Nonnull WingCustomizationData data) { return valid; }
}
