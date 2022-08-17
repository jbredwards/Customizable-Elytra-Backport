package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.text.TextFormatting;
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
    public EnumActionResult changeEquipmentColor(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing) {
        if(!data.hasTag("Banner")) {
            final float[] rgb = new Color(dyeColor).getRGBColorComponents(new float[3]);
            GlStateManager.color(rgb[0], rgb[1], rgb[2]);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void writeToNBT(@Nonnull NBTTagCompound compound) { compound.setInteger("DyeColor", dyeColor); }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) { dyeColor = compound.getInteger("DyeColor"); }

    @Override
    public void onAddedToData(@Nonnull WingCustomizationData data) { data.baseColor = dyeColor; }

    @Override
    public void onDataRemoved(@Nonnull WingCustomizationData data, boolean removed) {
        if(removed) data.baseColor = -1;
        else if(data.baseColor == -1) data.baseColor = dyeColor;
    }
}
