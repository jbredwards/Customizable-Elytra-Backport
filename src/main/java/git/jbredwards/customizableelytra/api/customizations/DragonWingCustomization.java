package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Added for quark dragon scale elytra duplication
 * @author jbred
 *
 */
public final class DragonWingCustomization implements IWingCustomization
{
    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.RED + I18n.format("customizableelytra.tooltip.dragon"));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public EnumActionResult changeEquipmentColor(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing) {
        GlStateManager.color(0, 0, 0);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean onWash(@Nonnull WingCustomizationData data) { return false; }

    @Override
    public boolean isConflictingWith(@Nonnull WingCustomizationData data, @Nonnull IWingCustomization other) { return true; }

    @Override
    public void onAddedToData(@Nonnull WingCustomizationData data) { data.baseColor = 0; }
}
