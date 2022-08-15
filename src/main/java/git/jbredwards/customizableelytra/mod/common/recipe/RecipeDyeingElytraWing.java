package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Lets players dye their elytra wings like leather armor
 * @author jbred
 *
 */
public class RecipeDyeingElytraWing extends AbstractDyeingRecipe
{
    @Override
    public boolean isDyeableTarget(@Nonnull ItemStack stack) { return IWingCapability.get(stack) != null; }

    @Override
    public void applyDyesToStack(@Nonnull ItemStack stack, @Nonnull List<EnumDyeColor> dyes) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) applyDyesToWing(cap, dyes);
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }
}
