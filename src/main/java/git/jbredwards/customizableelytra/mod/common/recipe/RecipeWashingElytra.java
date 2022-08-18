package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players wash off all their elytra customizations at once
 * @author jbred
 *
 */
public class RecipeWashingElytra extends AbstractWashingRecipe
{
    @Override
    public boolean isCustomizable(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        return cap != null &&
                (cap.getLeftWing().getData().getLastCustomization().onWash(cap.getLeftWing().getData(), false)
                || cap.getRightWing().getData().getLastCustomization().onWash(cap.getRightWing().getData(), false));
    }

    @Override
    public void applyWashingToStack(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            applyWashingToWing(cap.getLeftWing());
            applyWashingToWing(cap.getRightWing());

            cap.setAreWingsDuplicates(cap.getLeftWing().getData().serializeNBT()
                    .equals(cap.getRightWing().getData().serializeNBT()));
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }
}
