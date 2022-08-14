package git.jbredwards.customizableelytra.mod.common.init;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.item.ItemElytraWing;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
public final class ModItems
{
    @Nonnull
    public static final Item WING = new ItemElytraWing()
            .setRegistryName(Constants.MODID, "wing")
            .setTranslationKey(Constants.MODID + ".wing")
            .setCreativeTab(CreativeTabs.MISC)
            .setMaxStackSize(2);
}
