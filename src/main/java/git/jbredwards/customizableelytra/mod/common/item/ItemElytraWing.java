package git.jbredwards.customizableelytra.mod.common.item;

import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
public class ItemElytraWing extends Item
{
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) cap.getData().forEach(customization ->
                customization.addTooltip(stack, cap.getData(), tooltip, flagIn.isAdvanced()));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
