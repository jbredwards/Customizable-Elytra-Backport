package git.jbredwards.customizableelytra.mod.common;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.item.ItemElytraWing;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class EventHandler
{
    @SubscribeEvent
    public static void onWashElytra(@Nonnull PlayerInteractEvent.RightClickBlock event) {
        final @Nullable IElytraCapability heldCap = IElytraCapability.get(event.getItemStack());
        if(heldCap != null) {
            final IBlockState state = event.getWorld().getBlockState(event.getPos());
            if(state.getBlock() instanceof BlockCauldron) {
                final int level = state.getValue(BlockCauldron.LEVEL);
                if(level > 0) {
                    final ItemStack copy = ItemHandlerHelper.copyStackWithSize(event.getItemStack(), 1);
                    final @Nullable IElytraCapability cap = IElytraCapability.get(copy);
                    if(cap != null) {
                        final EntityPlayer player = event.getEntityPlayer();
                        final EnumHand hand = event.getHand();
                        final EnumHandSide handSide = getSideFromHand(hand, player.getPrimaryHand());

                        boolean washedLeft = false;
                        boolean washedRight = false;

                        if((!player.isSneaking() || handSide == EnumHandSide.RIGHT) && ItemElytraWing.wash(cap.getLeftWing())) washedLeft = true;
                        if((!player.isSneaking() || handSide == EnumHandSide.LEFT) && ItemElytraWing.wash(cap.getRightWing())) washedRight = true;

                        if(washedLeft || washedRight) {
                            if(!player.isCreative()) {
                                event.getItemStack().shrink(1);
                                ((BlockCauldron)state.getBlock()).setWaterLevel(event.getWorld(), event.getPos(), state, level - 1);
                            }

                            cap.setAreWingsDuplicates(cap.getLeftWing().getData().serializeNBT().equals(cap.getRightWing().getData().serializeNBT()));

                            if(event.getItemStack().isEmpty()) player.setHeldItem(hand, copy);
                            else if(!player.inventory.addItemStackToInventory(copy))
                                player.dropItem(copy, false);

                            player.addStat(StatList.ARMOR_CLEANED);

                            event.setCanceled(true);
                            event.setCancellationResult(EnumActionResult.SUCCESS);
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    static EnumHandSide getSideFromHand(@Nonnull EnumHand hand, @Nonnull EnumHandSide primaryHand) {
        if(primaryHand == EnumHandSide.LEFT) {
            if(hand == EnumHand.MAIN_HAND) return EnumHandSide.LEFT;
            else return EnumHandSide.RIGHT;
        }

        else {
            if(hand == EnumHand.MAIN_HAND) return EnumHandSide.RIGHT;
            else return EnumHandSide.LEFT;
        }
    }
}
