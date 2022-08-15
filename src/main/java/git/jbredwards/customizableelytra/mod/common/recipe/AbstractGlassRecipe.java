package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.WingCustomizationData;
import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.recipe.core.AbstractDynamicRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractGlassRecipe extends AbstractDynamicRecipe
{
    public abstract boolean isCustomizable(@Nonnull ItemStack stack);
    public abstract void applyGlassToStack(@Nonnull ItemStack stack);
    public void applyGlassToWing(@Nonnull IWingCapability cap) {
        final WingCustomizationData data = WingCustomizationData.copyOf(cap.getData());
        data.addCustomization(WingCustomizations.GLASS);
        cap.setData(data);
    }

    protected boolean isItemGlass(@Nonnull ItemStack stack) {
        return ArrayUtils.contains(OreDictionary.getOreIDs(stack), OreDictionary.getOreID("blockGlass"));
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundElytra = false, foundGlowstone = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(!foundElytra && isCustomizable(stack)) {
                    foundElytra = true;
                    continue;
                }

                if(!foundGlowstone && isItemGlass(stack)) {
                    foundGlowstone = true;
                    continue;
                }

                return false;
            }
        }

        return foundElytra && foundGlowstone;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack elytra = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(isCustomizable(stack)) {
                    elytra = stack.copy();
                    break;
                }
            }
        }

        applyGlassToStack(elytra);
        return elytra;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        final NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int i = 0; i < list.size(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty() && !isCustomizable(stack))
                list.set(i, ForgeHooks.getContainerItem(stack));
        }

        return list;
    }

    @Override
    public boolean canFit(int width, int height) { return width * height >= 2; }
}
