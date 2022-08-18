package git.jbredwards.customizableelytra.mod.common.item;

import git.jbredwards.customizableelytra.api.WingCustomizationData;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

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
    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        final ItemStack held = player.getHeldItem(hand);
        final @Nullable IWingCapability heldCap = IWingCapability.get(held);

        if(heldCap != null && heldCap.getData().size() > 0) {
            final IBlockState state = world.getBlockState(pos);
            if(state.getBlock() instanceof BlockCauldron) {
                final int waterLevel = state.getValue(BlockCauldron.LEVEL);
                if(waterLevel > 0) {
                    final ItemStack copy = ItemHandlerHelper.copyStackWithSize(held, 1);
                    if(wash(IWingCapability.get(copy))) {
                        if(!player.isCreative()) {
                            held.shrink(1);
                            ((BlockCauldron)state.getBlock()).setWaterLevel(world, pos, state, waterLevel - 1);
                        }

                        if(held.isEmpty()) player.setHeldItem(hand, copy);
                        else if(!player.inventory.addItemStackToInventory(copy))
                            player.dropItem(copy, false);

                        player.addStat(StatList.ARMOR_CLEANED);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    public static boolean wash(@Nullable IWingCapability cap) {
        if(cap != null && cap.getData().size() > 0) {
            final WingCustomizationData data = WingCustomizationData.copyOf(cap.getData());
            if(!data.getLastCustomization().onWash(data, true)) return false;

            cap.setData(data);
            return true;
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        final @Nullable IWingCapability cap = IWingCapability.get(stack);
        if(cap != null) cap.getData().forEach(customization ->
                customization.addTooltip(stack, cap.getData(), tooltip, flagIn.isAdvanced()));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
