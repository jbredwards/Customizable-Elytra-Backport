package git.jbredwards.customizableelytra.mod;

import git.jbredwards.customizableelytra.api.event.WingCustomizationRegistryEvent;
import git.jbredwards.customizableelytra.mod.client.layer.LayerCustomizableElytra;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.mod.common.capability.IWingCapability;
import git.jbredwards.customizableelytra.mod.common.compat.QuarkColorTagFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;
import java.util.List;

/**
 *
 * @author jbred
 *
 */
@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public final class Main
{
    @SuppressWarnings("NotNullFieldNotInitialized")
    @SidedProxy(clientSide = "git.jbredwards.customizableelytra.mod.Main$ClientProxy", serverSide = "git.jbredwards.customizableelytra.mod.Main$CommonProxy")
    @Nonnull public static CommonProxy proxy;

    @Mod.EventHandler
    static void preInit(@Nonnull FMLPreInitializationEvent event) { proxy.preInit(); }

    @Mod.EventHandler
    static void init(@Nonnull FMLInitializationEvent event) { proxy.init(); }

    @Mod.EventHandler
    static void postInit(@Nonnull FMLPostInitializationEvent event) { proxy.postInit(); }

    //handles server-side code
    public static class CommonProxy
    {
        protected void preInit() {
            MinecraftForge.EVENT_BUS.post(new WingCustomizationRegistryEvent());
            CapabilityManager.INSTANCE.register(IElytraCapability.class, IElytraCapability.Storage.INSTANCE, IElytraCapability.Impl::new);
            CapabilityManager.INSTANCE.register(IWingCapability.class, IWingCapability.Storage.INSTANCE, IWingCapability.Impl::new);
        }

        protected void init() {
            //convert quark color data to this mod's format
            if(Loader.isModLoaded("quark")) FMLCommonHandler.instance().getDataFixer()
                    .init(Constants.MODID, QuarkColorTagFix.DATA_VERSION)
                    .registerFix(FixTypes.ITEM_INSTANCE, new QuarkColorTagFix());
        }

        protected void postInit() {}
    }

    //handles client-side code
    @SuppressWarnings("unused")
    public static class ClientProxy extends CommonProxy
    {
        @SuppressWarnings({"unchecked", "RedundantCast"})
        @Override
        protected void postInit() {
            //swap entity elytra layer
            Minecraft.getMinecraft().getRenderManager().entityRenderMap.values().forEach(renderer -> {
                if(renderer instanceof RenderLivingBase) {
                    //get around different types through weird casting
                    ((List<LayerRenderer<?>>)(Object)((RenderLivingBase<?>)renderer).layerRenderers).removeIf(layer -> layer instanceof LayerElytra);
                    ((RenderLivingBase<?>)renderer).addLayer(new LayerCustomizableElytra((RenderLivingBase<?>)renderer));
                }
            });

            //swap player elytra layer
            Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(renderer -> {
                ((List<LayerRenderer<?>>)(Object)renderer.layerRenderers).removeIf(layer -> layer instanceof LayerElytra);
                renderer.addLayer(new LayerCustomizableElytra(renderer));
            });
        }
    }
}
