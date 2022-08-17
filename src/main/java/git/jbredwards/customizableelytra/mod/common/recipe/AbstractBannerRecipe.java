package git.jbredwards.customizableelytra.mod.common.recipe;

import git.jbredwards.customizableelytra.api.WingCustomizationData;
import git.jbredwards.customizableelytra.api.customizations.BannerWingCustomization;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.recipe.core.AbstractDynamicRecipe;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
public abstract class AbstractBannerRecipe extends AbstractDynamicRecipe
{
    public abstract boolean isCustomizable(@Nonnull ItemStack stack);
    public abstract void applyBannerToStack(@Nonnull ItemStack stack, @Nonnull NBTTagList patterns, @Nonnull EnumDyeColor bannerColor);
    public void applyBannerToWing(@Nonnull IWingCapability cap, @Nonnull NBTTagList patterns, @Nonnull EnumDyeColor bannerColor) {
        final WingCustomizationData data = WingCustomizationData.copyOf(cap.getData());
        data.addCustomization("Banner", new BannerWingCustomization(patterns, bannerColor));
        cap.setData(data);
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundElytra = false, foundBanner = false;
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(!foundElytra && isCustomizable(stack)) {
                    foundElytra = true;
                    continue;
                }

                if(!foundBanner && stack.getItem() == Items.BANNER) {
                    foundBanner = true;
                    continue;
                }

                return false;
            }
        }

        return foundElytra && foundBanner;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack elytra = ItemStack.EMPTY;
        ItemStack banner = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            final ItemStack stack = inv.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(elytra.isEmpty() && isCustomizable(stack)) {
                    elytra = ItemHandlerHelper.copyStackWithSize(stack, 1);
                    continue;
                }

                if(banner.isEmpty() && stack.getItem() == Items.BANNER) {
                    banner = stack;
                    continue;
                }

                //should never pass
                return getRecipeOutput();
            }
        }

        final EnumDyeColor color = ItemBanner.getBaseColor(banner);
        NBTTagList patterns = new NBTTagList();

        final @Nullable NBTTagCompound bannerNbt = banner.getTagCompound();
        if(bannerNbt != null) patterns = bannerNbt.getCompoundTag("BlockEntityTag").getTagList("Patterns", 10);

        applyBannerToStack(elytra, patterns, color);
        return elytra;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public boolean canFit(int width, int height) { return width * height >= 2; }
}
