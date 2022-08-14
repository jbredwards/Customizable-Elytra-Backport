package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.DyeUtils;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractDyeingRecipe extends AbstractDynamicRecipe
{
    public abstract boolean isDyeableTarget(@Nonnull ItemStack stack);
    public abstract void applyDyesToStack(@Nonnull ItemStack stack, @Nonnull List<EnumDyeColor> dyes);

    public void applyDyesToWing(@Nonnull IWingCapability cap, @Nonnull List<EnumDyeColor> dyes) {
        final WingCustomizationData data = WingCustomizationData.copyOf(cap.getData());
        data.baseColor = getResultDyeColor(data.baseColor, dyes);
        data.addCustomization(WingCustomizations.DYE);

        cap.setData(data);
    }

    public int getResultDyeColor(int baseColor, @Nonnull List<EnumDyeColor> dyes) {
        final int size = dyes.size() + (baseColor != -1 ? 1 : 0);
        float r = baseColor != -1 ? (baseColor >> 16 & 0xFF) / 255f : 0;
        float g = baseColor != -1 ? (baseColor >> 8 & 0xFF) / 255f : 0;
        float b = baseColor != -1 ? (baseColor & 0xFF) / 255f : 0;

        for(EnumDyeColor dye : dyes) {
            final float[] rgb = dye.getColorComponentValues();
            r += rgb[0];
            g += rgb[1];
            b += rgb[2];
        }

        return new Color(r / size, g / size, b / size).getRGB();
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundDyeable = false, foundDye = false;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(!foundDyeable && isDyeableTarget(stack)) {
                    foundDyeable = true;
                    continue;
                }

                if(DyeUtils.isDye(stack)) {
                    foundDye = true;
                    continue;
                }

                return false;
            }
        }

        return foundDyeable && foundDye;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        final List<EnumDyeColor> dyes = new ArrayList<>();
        ItemStack dyeable = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(dyeable.isEmpty() && isDyeableTarget(stack)) {
                    dyeable = stack.copy();
                    continue;
                }

                else if(DyeUtils.isDye(stack)) {
                    final Optional<EnumDyeColor> color = DyeUtils.colorFromStack(stack);
                    if(color.isPresent()) {
                        dyes.add(color.get());
                        continue;
                    }
                }

                //should never pass
                return getRecipeOutput();
            }
        }

        applyDyesToStack(dyeable, dyes);
        return dyeable;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        final NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for(int i = 0; i < list.size(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty() && !isDyeableTarget(stack))
                list.set(i, ForgeHooks.getContainerItem(stack));
        }

        return list;
    }

    @Override
    public boolean canFit(int width, int height) { return width * height >= 2; }
}
