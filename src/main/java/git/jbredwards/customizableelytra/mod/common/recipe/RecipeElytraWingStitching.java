package git.jbredwards.customizableelytra.mod.common.recipe;

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
 * Lets players combine two wings to make an elytra
 * @author jbred
 *
 */
public class RecipeElytraWingStitching extends AbstractDynamicRecipe
{
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        @Nullable IWingCapability left = null, right = null;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(left == null) {
                    left = IWingCapability.get(stack);
                    if(left != null) continue;
                }

                if(right == null) {
                    right = IWingCapability.get(stack);
                    if(right != null && left != null && right.getCombineItem().equals(left.getCombineItem())) continue;
                }

                return false;
            }
        }

        return left != null && right != null;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        @Nullable IWingCapability left = null, right = null;
        ItemStack elytra = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(left == null) {
                    left = IWingCapability.get(stack);
                    if(left != null) continue;
                }

                if(right == null) {
                    right = IWingCapability.get(stack);
                    if(right != null) {
                        elytra = new ItemStack(right.getCombineItem());
                        continue;
                    }
                }

                //should never pass
                return getRecipeOutput();
            }
        }

        if(left != null && right != null) {
            final @Nullable IElytraCapability cap = IElytraCapability.get(elytra);
            if(cap != null) {
                cap.setLeftWing(left);
                cap.setRightWing(right);

                cap.setAreWingsDuplicates(left.getData().serializeNBT()
                        .equals(right.getData().serializeNBT()));
            }
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
