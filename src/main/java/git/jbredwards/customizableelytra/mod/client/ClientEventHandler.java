package git.jbredwards.customizableelytra.mod.client;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.client.util.TooltipHandler;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
import git.jbredwards.customizableelytra.mod.common.util.CustomizationType;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID, value = Side.CLIENT)
public final class ClientEventHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handleItemColors(@Nonnull ColorHandlerEvent.Item event) {
        //set elytra color handler
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            final @Nullable IElytraCapability cap = IElytraCapability.get(stack);
            if(cap != null) switch(tintIndex) {
                case 0: return cap.getLeftWing().getData().color;
                case 1: return cap.getRightWing().getData().color;
            }

            return -1;
        }, Items.ELYTRA);

        //set elytra wing color handler
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            final @Nullable IWingCapability cap = IWingCapability.get(stack);
            if(cap != null) return cap.getData().color;

            return -1;
        }, ModItems.WING);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void handleElytraTooltips(@Nonnull ItemTooltipEvent event) {
        final @Nullable IElytraCapability cap = IElytraCapability.get(event.getItemStack());
        if(cap != null) {
            if(cap.areWingsDuplicates()) TooltipHandler.addTooltip(cap.getLeftWing().getData(), event.getToolTip(), event.getFlags().isAdvanced());
            else {
                final IWingCapability left = cap.getLeftWing();
                final IWingCapability right = cap.getRightWing();
                boolean addedLeftInfo = false;

                if(left.getData().type != CustomizationType.NONE) {
                    event.getToolTip().add(I18n.format("customizableelytra.tooltip.left"));
                    TooltipHandler.addTooltip(left.getData(), event.getToolTip(), event.getFlags().isAdvanced());
                    addedLeftInfo = true;
                }

                if(right.getData().type != CustomizationType.NONE) {
                    if(addedLeftInfo) event.getToolTip().add("");
                    event.getToolTip().add(I18n.format("customizableelytra.tooltip.right"));
                    TooltipHandler.addTooltip(right.getData(), event.getToolTip(), event.getFlags().isAdvanced());
                }
            }
        }
    }
}
