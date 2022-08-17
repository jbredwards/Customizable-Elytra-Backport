package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.WingCustomizationEntry;
import git.jbredwards.customizableelytra.api.event.WingCustomizationRegistryEvent;
import git.jbredwards.customizableelytra.mod.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * The default wing customizations added by this mod
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class WingCustomizations
{
    @Nonnull public static final WingCustomizationEntry BANNER = new WingCustomizationEntry("Banner", BannerWingCustomization::new);
    @Nonnull public static final WingCustomizationEntry DRAGON = new WingCustomizationEntry("Dragon", DragonWingCustomization::new);
    @Nonnull public static final WingCustomizationEntry DYE = new WingCustomizationEntry("Dye", DyeWingCustomization::new);
    @Nonnull public static final WingCustomizationEntry GLASS = new WingCustomizationEntry("Glass", GlassWingCustomization::new);
    @Nonnull public static final WingCustomizationEntry GLOWING = new WingCustomizationEntry("Glowing", GlowingWingCustomization::new);

    @SubscribeEvent
    public static void registerDefaultCustomizations(@Nonnull WingCustomizationRegistryEvent event) {
        event.registerAll(BANNER, DRAGON, DYE, GLASS, GLOWING);
    }
}
