package git.jbredwards.customizableelytra.mod.client;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraData;
import git.jbredwards.customizableelytra.mod.common.capability.IWingData;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    @SideOnly(Side.CLIENT)
    public static final IItemColor ELYTRA_HANDLER = (stack, tintIndex) -> {
        @Nullable final IElytraData cap = IElytraData.get(stack);
        if(cap != null) switch(tintIndex) {
            case 0: return cap.getLeftWing().getData().color;
            case 1: return cap.getRightWing().getData().color;
        }

        return -1;
    };

    @SideOnly(Side.CLIENT)
    public static final IItemColor WING_HANDLER = (stack, tintIndex) -> {
        @Nullable final IWingData cap = IWingData.get(stack);
        if(cap != null) return cap.getData().color;

        return -1;
    };

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void handleItemColors(@Nonnull ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler(ELYTRA_HANDLER, Items.ELYTRA);
    }
}
