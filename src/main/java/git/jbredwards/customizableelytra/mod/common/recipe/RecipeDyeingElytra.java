package git.jbredwards.customizableelytra.mod.common.recipe;

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
    public boolean isDyeableTarget(@Nonnull ItemStack stack) { return IElytraCapability.get(stack) != null; }

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
