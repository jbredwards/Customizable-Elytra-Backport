package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.util.CustomizationType;
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
public class RecipeElytraDyeing extends AbstractDyeingRecipe
{
    @Override
    public boolean isDyeableTarget(@Nonnull ItemStack stack) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        return cap != null
                && (cap.getLeftWing().getData().type != CustomizationType.BANNER
                || cap.getRightWing().getData().type != CustomizationType.BANNER);
    }

    @Override
    public void applyDyesToStack(@Nonnull ItemStack stack, @Nonnull List<EnumDyeColor> dyes) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            if(cap.getLeftWing().getData().type != CustomizationType.BANNER)
                applyDyesToWing(cap.getLeftWing(), dyes);

            if(cap.getRightWing().getData().type != CustomizationType.BANNER)
                applyDyesToWing(cap.getRightWing(), dyes);
        }
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }
}
