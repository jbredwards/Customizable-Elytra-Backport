package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players make their elytra wings glow
 * @author jbred
 *
 */
public class RecipeGlowingElytraWing extends AbstractGlowingRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) { return IWingCapability.get(stack) != null; }

    @Override
    public void applyGlowingToStack(@Nonnull ItemStack stack) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) applyGlowingToWing(cap);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }
}
