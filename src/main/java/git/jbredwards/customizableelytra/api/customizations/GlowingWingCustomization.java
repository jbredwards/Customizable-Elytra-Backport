package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import git.jbredwards.customizableelytra.mod.client.layer.LayerCustomizableElytra;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
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
    public void preRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!data.hasTag("Glass")) {
            GlStateManager.disableLighting();
            prevLastBrightnessX = OpenGlHelper.lastBrightnessX;
            prevLastBrightnessY = OpenGlHelper.lastBrightnessY;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 0);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!data.hasTag("Glass")) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevLastBrightnessX, prevLastBrightnessY);
            GlStateManager.scale(1.002, 1.002, 1.002);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            GlStateManager.enableLighting();
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.001, -0.001, -0.001);
            LayerCustomizableElytra.renderModel(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.translate(0.001, 0.001, 0.001);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.001, 0.001, 0.001);
            LayerCustomizableElytra.renderModel(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.translate(-0.001, -0.001, -0.001);
            GlStateManager.popMatrix();
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }
}
