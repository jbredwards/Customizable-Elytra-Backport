package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players make their elytra translucent
 * @author jbred
 *
 */
public class RecipeGlassElytraWing extends AbstractGlassRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) { return IWingCapability.get(stack) != null; }

    @Override
    public void applyGlassToStack(@Nonnull ItemStack stack) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) applyGlassToWing(cap);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }
}
