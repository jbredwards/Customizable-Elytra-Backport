package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
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
public final class GlassWingCustomization implements IWingCustomization
{
    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("customizableelytra.tooltip.glass"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void preRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderPlayer renderer, @Nonnull AbstractClientPlayer entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.color(GlStateManager.colorState.red, GlStateManager.colorState.green, GlStateManager.colorState.blue, 0.5f);
        GlStateManager.depthMask(false);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        if(data.hasTag("Glowing")) GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        else GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void postRender(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderPlayer renderer, @Nonnull AbstractClientPlayer entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.depthMask(true);
    }
}
