package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Lets players dye their elytra like leather armor
 * @author jbred
 *
 */
public class RecipeDyeingElytra extends AbstractDyeingRecipe
{
    @Override
    public boolean isDyeableTarget(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        return cap != null
                && (!cap.getLeftWing().getData().hasTag("Banner") && cap.getLeftWing().getData().isCompatible(WingCustomizations.DYE)
                || !cap.getRightWing().getData().hasTag("Banner") && cap.getRightWing().getData().isCompatible(WingCustomizations.DYE));
    }

    @Override
    public void applyDyesToStack(@Nonnull ItemStack stack, @Nonnull List<EnumDyeColor> dyes) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            applyDyesToWing(cap.getLeftWing(), dyes);
            applyDyesToWing(cap.getRightWing(), dyes);
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }
}
