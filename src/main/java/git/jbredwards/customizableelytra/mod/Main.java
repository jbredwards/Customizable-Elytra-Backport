package git.jbredwards.customizableelytra.mod;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
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
        CapabilityManager.INSTANCE.register(IElytraCapability.class, IElytraCapability.Storage.INSTANCE, IElytraCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IWingCapability.class, IWingCapability.Storage.INSTANCE, IWingCapability.Impl::new);
    }
}
