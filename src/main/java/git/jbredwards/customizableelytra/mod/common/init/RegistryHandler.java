package git.jbredwards.customizableelytra.mod.common.init;

import git.jbredwards.customizableelytra.mod.Constants;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@Mod.EventBusSubscriber(modid = Constants.MODID)
public final class RegistryHandler
{
    @SubscribeEvent
    public static void registerItems(@Nonnull RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ModItems.WING);
    }

    @SubscribeEvent
    public static void registerRecipes(@Nonnull RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(ModRecipes.RECIPES.toArray(new IRecipe[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemModels(@Nonnull ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(ModItems.WING, 0,
                new ModelResourceLocation(new ResourceLocation(Constants.MODID, "wing"), "inventory"));
    }
}
