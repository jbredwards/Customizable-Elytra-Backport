package git.jbredwards.customizableelytra.mod.common.recipe.core;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public class RecipeElytraWings extends AbstractDynamicRecipe
{
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundElytra = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(!foundElytra && !stack.isItemDamaged() && IElytraCapability.get(stack) != null) {
                    foundElytra = true;
                    continue;
                }

                return false;
            }
        }

        return foundElytra;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
                if(cap != null) {
                    final ItemStack wing = new ItemStack(cap.getWingItem(), cap.areWingsDuplicates() ? 2 : 1);
                    final @Nullable IWingCapability wingCap = IWingCapability.get(wing);
                    if(wingCap != null) {
                        wingCap.setData(cap.getRightWing().getData());
                        return wing;
                    }
                }
            }
        }

        //should never pass
        return getRecipeOutput();
    }

    @Override
    public boolean canFit(int width, int height) { return width * height >= 1; }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() { return new ItemStack(ModItems.WING); }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        final NonNullList<ItemStack> list = NonNullList.create();
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final @Nullable IElytraCapability cap = IElytraCapability.get(inv.getStackInSlot(i));
            if(cap == null || cap.areWingsDuplicates()) list.add(ItemStack.EMPTY);
            else {
                final ItemStack wing = new ItemStack(cap.getWingItem());
                final @Nullable IWingCapability wingCap = IWingCapability.get(wing);
                if(wingCap != null) wingCap.setData(cap.getLeftWing().getData());

                list.add(wing);
            }
        }

        return list;
    }
}
