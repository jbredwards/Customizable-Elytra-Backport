package git.jbredwards.customizableelytra.mod.common.compat;

import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import git.jbredwards.customizableelytra.mod.common.recipe.core.RecipeElytraWingStitching;
import git.jbredwards.customizableelytra.mod.common.recipe.core.RecipeElytraWings;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@JEIPlugin
public final class JeiRecipes implements IModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry) {
        //elytra from wings recipe
        registry.handleRecipes(RecipeElytraWingStitching.class, recipe -> ingredients -> {
            ingredients.setInputs(VanillaTypes.ITEM, NonNullList.withSize(2, new ItemStack(ModItems.WING)));
            ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(Items.ELYTRA));
        }, VanillaRecipeCategoryUid.CRAFTING);
        //wings from elytra recipe
        registry.handleRecipes(RecipeElytraWings.class, recipe -> ingredients -> {
            ingredients.setInputs(VanillaTypes.ITEM, NonNullList.withSize(1, new ItemStack(Items.ELYTRA)));
            ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(ModItems.WING, 2));
        }, VanillaRecipeCategoryUid.CRAFTING);
    }
}
