package git.jbredwards.customizableelytra.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.recipe.*;
import git.jbredwards.customizableelytra.mod.common.recipe.core.RecipeElytraWingStitching;
import git.jbredwards.customizableelytra.mod.common.recipe.core.RecipeElytraWingSwap;
import net.minecraft.item.crafting.IRecipe;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ModRecipes
{
    @Nonnull
    public static final ImmutableList<IRecipe> RECIPES = ImmutableList.<IRecipe>builder()
            //core
            .add(new RecipeElytraWingStitching().setRegistryName(Constants.MODID, "elytra_wing_switching"))
            .add(new RecipeElytraWingSwap().setRegistryName(Constants.MODID, "elytra_wing_swap"))
            //customization
            .add(new RecipeDyeingElytra().setRegistryName(Constants.MODID, "dyeable_elytra"))
            .add(new RecipeDyeingElytraWing().setRegistryName(Constants.MODID, "dyeable_elytra_wings"))
            .add(new RecipeGlowingElytra().setRegistryName(Constants.MODID, "glowing_elytra"))
            .add(new RecipeGlowingElytraWing().setRegistryName(Constants.MODID, "glowing_elytra_wings"))
            .build();
}
