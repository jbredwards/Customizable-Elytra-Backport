package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
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
public class RecipeBannerElytraWing extends AbstractBannerRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        return cap != null && cap.getData().isCompatible(WingCustomizations.BANNER);
    }

    @Override
    public void applyBannerToStack(@Nonnull ItemStack stack, @Nonnull NBTTagList patterns, @Nonnull EnumDyeColor bannerColor) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) applyBannerToWing(cap, patterns, bannerColor);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }
}
