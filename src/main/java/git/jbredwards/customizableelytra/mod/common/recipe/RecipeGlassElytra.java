package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players make their elytra translucent
 * @author jbred
 *
 */
public class RecipeGlassElytra extends AbstractGlassRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) { return IElytraCapability.get(stack) != null; }

    @Override
    public void applyGlassToStack(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            applyGlassToWing(cap.getLeftWing());
            applyGlassToWing(cap.getRightWing());
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }
}
