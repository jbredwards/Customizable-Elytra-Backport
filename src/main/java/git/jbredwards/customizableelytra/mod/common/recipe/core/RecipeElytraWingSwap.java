package git.jbredwards.customizableelytra.mod.common.recipe.core;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Lets players swap one wing for another, this functions similarly to tconstruct tools
 * @author jbred
 *
 */
public class RecipeElytraWingSwap extends AbstractDynamicRecipe
{
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundElytra = false, foundLeftWing = false, foundRightWing = false;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(!foundElytra) {
                    if(!foundLeftWing && IWingCapability.get(stack) != null) {
                        foundLeftWing = true;
                        continue;
                    }

                    if(IElytraCapability.get(stack) != null) {
                        foundElytra = true;
                        continue;
                    }
                }

                else if(!foundRightWing && IWingCapability.get(stack) != null) {
                    foundRightWing = true;
                    continue;
                }

                return false;
            }
        }

        return foundElytra && (foundLeftWing || foundRightWing);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        @Nullable IWingCapability leftWing = null, rightWing = null;
        ItemStack elytra = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(elytra.isEmpty()) {
                    if(leftWing == null) {
                        leftWing = IWingCapability.get(stack);
                        if(leftWing != null) continue;
                    }

                    if(IElytraCapability.get(stack) != null) {
                        elytra = stack.copy();
                        continue;
                    }
                }

                else if(rightWing == null) {
                    rightWing = IWingCapability.get(stack);
                    if(rightWing != null) continue;
                }

                //should never pass
                return getRecipeOutput();
            }
        }

        @Nullable final IElytraCapability cap = IElytraCapability.get(elytra);
        if(cap != null) {
            if(leftWing != null) cap.setLeftWing(leftWing);
            if(rightWing != null) cap.setRightWing(rightWing);
            cap.setAreWingsDuplicates(false);
        }

        return elytra;
    }

    @Override
    public boolean canFit(int width, int height) { return width * height >= 2; }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(Items.ELYTRA); }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }
}
