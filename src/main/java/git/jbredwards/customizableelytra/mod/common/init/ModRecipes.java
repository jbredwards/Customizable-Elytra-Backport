package git.jbredwards.customizableelytra.mod.common.init;

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
    @Nonnull public static final IRecipe ELYTRA_DYEING = new ElytraDyeingRecipe().setRegistryName(Constants.MODID, "dyeable_elytra");
    @Nonnull public static final IRecipe ELYTRA_WING_DYEING = new ElytraWingDyeingRecipe().setRegistryName(Constants.MODID, "dyeable_elytra_wings");
    @Nonnull public static final IRecipe ELYTRA_WING_SWAP = new ElytraWingSwapRecipe().setRegistryName(Constants.MODID, "elytra_wing_swap");
}