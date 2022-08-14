package git.jbredwards.customizableelytra.api.event;

import git.jbredwards.customizableelytra.api.WingCustomizationEntry;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

/**
 * Fired when it's time to register wing customizations
 * @author jbred
 *
 */
public class WingCustomizationRegistryEvent extends Event
{
    public void register(@Nonnull WingCustomizationEntry entry) {
        WingCustomizationEntry.REGISTRY.put(entry.tagName, entry);
    }

    public void registerAll(@Nonnull WingCustomizationEntry... entries) {
        for(WingCustomizationEntry entry : entries) register(entry);
    }

    public void registerAll(@Nonnull Iterable<WingCustomizationEntry> entries) { entries.forEach(this::register); }
}
