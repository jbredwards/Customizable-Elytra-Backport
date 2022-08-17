package git.jbredwards.customizableelytra.asm;

import git.jbredwards.customizableelytra.api.customizations.WingCustomizations;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Changes the elytra dragon scale elytra duplication recipe to use this mod's customization handler
 * @author jbred
 *
 */
@SuppressWarnings("unused")
public final class QuarkASMHooks
{
    public static void setInt(@Nonnull ItemStack stack, @Nonnull String ignored, int color) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
        if(cap != null) {
            cap.getLeftWing().getData().addCustomization(WingCustomizations.DRAGON);
            cap.getRightWing().getData().addCustomization(WingCustomizations.DRAGON);
        }
    }
}
