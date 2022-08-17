package git.jbredwards.customizableelytra.mod.common.capability;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.item.ItemElytraWing;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = Constants.MODID)
public interface IWingCapability
{
    @CapabilityInject(IWingCapability.class)
    @Nonnull Capability<IWingCapability> CAPABILITY = null;

    @Nonnull WingCustomizationData getData();
    void setData(@Nonnull WingCustomizationData dataIn);

    @Nonnull
    default Item getCombineItem() { return Items.ELYTRA; }

    @Nullable
    static IWingCapability get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() instanceof ItemElytraWing)
            event.addCapability(new ResourceLocation(Constants.MODID, "wing"),
                    new CapabilityProvider<>(() -> CAPABILITY, new Impl()));
    }

    final class Impl implements IWingCapability
    {
        @Nonnull
        WingCustomizationData data = new WingCustomizationData();

        @Nonnull
        @Override
        public WingCustomizationData getData() { return data; }

        @Override
        public void setData(@Nonnull WingCustomizationData dataIn) { data = dataIn; }
    }

    enum Storage implements Capability.IStorage<IWingCapability>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IWingCapability> capability, @Nonnull IWingCapability instance, @Nullable EnumFacing side) {
            return instance.getData().serializeNBT();
        }

        @Override
        public void readNBT(@Nonnull Capability<IWingCapability> capability, @Nonnull IWingCapability instance, @Nullable EnumFacing side, @Nullable NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) instance.getData().deserializeNBT((NBTTagCompound)nbt);
        }
    }
}
