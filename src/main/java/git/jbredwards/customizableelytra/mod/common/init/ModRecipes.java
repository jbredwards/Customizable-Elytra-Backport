package git.jbredwards.customizableelytra.mod.common.init;

import com.google.common.collect.ImmutableList;
import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.recipe.*;
import git.jbredwards.customizableelytra.mod.common.recipe.core.*;
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
            .add(new RecipeElytraWings().setRegistryName(Constants.MODID, "elytra_wings"))
            //customization
            .add(new RecipeBannerElytra().setRegistryName(Constants.MODID, "banner_elytra"))
            .add(new RecipeBannerElytraWing().setRegistryName(Constants.MODID, "banner_elytra_wings"))
            .add(new RecipeDyeingElytra().setRegistryName(Constants.MODID, "dyeable_elytra"))
            .add(new RecipeDyeingElytraWing().setRegistryName(Constants.MODID, "dyeable_elytra_wings"))
            .add(new RecipeGlassElytra().setRegistryName(Constants.MODID, "glass_elytra"))
            .add(new RecipeGlassElytraWing().setRegistryName(Constants.MODID, "glass_elytra_wings"))
            .add(new RecipeGlowingElytra().setRegistryName(Constants.MODID, "glowing_elytra"))
            .add(new RecipeGlowingElytraWing().setRegistryName(Constants.MODID, "glowing_elytra_wings"))
            .add(new RecipeWashingElytra().setRegistryName(Constants.MODID, "washing_elytra"))
            .add(new RecipeWashingElytraWing().setRegistryName(Constants.MODID, "washing_elytra_wings"))
            .build();
}
