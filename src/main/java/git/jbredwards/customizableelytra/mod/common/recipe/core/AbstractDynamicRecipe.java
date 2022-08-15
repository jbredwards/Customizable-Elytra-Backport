package git.jbredwards.customizableelytra.mod.common.recipe.core;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractDynamicRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    @Override
    public boolean isDynamic() { return true; }
}
