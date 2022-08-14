package git.jbredwards.customizableelytra.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.recipe.*;
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
            .add(new RecipeElytraDyeing().setRegistryName(Constants.MODID, "dyeable_elytra"))
            .add(new RecipeElytraWingDyeing().setRegistryName(Constants.MODID, "dyeable_elytra_wings"))
            .add(new RecipeElytraWingStitching().setRegistryName(Constants.MODID, "elytra_wing_switching"))
            .add(new RecipeElytraWingSwap().setRegistryName(Constants.MODID, "elytra_wing_swap"))
            .build();
}
