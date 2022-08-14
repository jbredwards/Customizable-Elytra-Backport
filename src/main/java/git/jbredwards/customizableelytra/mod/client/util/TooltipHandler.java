package git.jbredwards.customizableelytra.mod.client.util;

import git.jbredwards.customizableelytra.mod.common.util.CustomizationType;
import git.jbredwards.customizableelytra.mod.common.util.ElytraWingData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public final class TooltipHandler
{
    @SideOnly(Side.CLIENT)
    public static void addTooltip(@Nonnull ElytraWingData data, @Nonnull List<String> tooltip, boolean advanced) {
        //handle color tooltip
        if(data.type == CustomizationType.DYE) {
            if(advanced) tooltip.add(I18n.format("item.color", String.format("#%06X", data.color)));
            else tooltip.add(TextFormatting.ITALIC + I18n.format("item.dyed"));
        }
    }
}
