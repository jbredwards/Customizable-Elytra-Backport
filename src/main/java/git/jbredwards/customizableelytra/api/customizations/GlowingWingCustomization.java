package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class GlowingWingCustomization implements IWingCustomization
{
    @SideOnly(Side.CLIENT)
    float prevLastBrightnessX, prevLastBrightnessY;

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("customizableelytra.tooltip.glowing"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderPlayer renderer, @Nonnull AbstractClientPlayer entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.disableLighting();
        prevLastBrightnessX = OpenGlHelper.lastBrightnessX;
        prevLastBrightnessY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderPlayer renderer, @Nonnull AbstractClientPlayer entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevLastBrightnessX, prevLastBrightnessY);
    }
}
