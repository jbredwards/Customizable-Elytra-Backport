package git.jbredwards.customizableelytra.mod;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraData;
import git.jbredwards.customizableelytra.mod.common.capability.IWingData;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public final class Main
{
    @Mod.EventHandler
    static void preInit(@Nonnull FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IElytraData.class, IElytraData.Storage.INSTANCE, IElytraData.Impl::new);
        CapabilityManager.INSTANCE.register(IWingData.class, IWingData.Storage.INSTANCE, IWingData.Impl::new);
    }
}
