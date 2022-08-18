package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players wash off all their elytra customizations at once
 * @author jbred
 *
 */
public class RecipeWashingElytraWing extends AbstractWashingRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        return cap != null && cap.getData().getLastCustomization().onWash(cap.getData(), false);
    }

    @Override
    public void applyWashingToStack(@Nonnull ItemStack stack) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) applyWashingToWing(cap);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }
}
