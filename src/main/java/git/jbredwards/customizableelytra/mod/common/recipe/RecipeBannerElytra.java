package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players make put banners on their elytra
 * @author jbred
 *
 */
public class RecipeBannerElytra extends AbstractBannerRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        return cap != null
                && (cap.getLeftWing().getData().isCompatible(WingCustomizations.BANNER)
                || cap.getRightWing().getData().isCompatible(WingCustomizations.BANNER));
    }

    @Override
    public void applyBannerToStack(@Nonnull ItemStack stack, @Nonnull NBTTagList patterns, @Nonnull EnumDyeColor bannerColor) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            applyBannerToWing(cap.getLeftWing(), patterns, bannerColor);
            applyBannerToWing(cap.getRightWing(), patterns, bannerColor);
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }
}
