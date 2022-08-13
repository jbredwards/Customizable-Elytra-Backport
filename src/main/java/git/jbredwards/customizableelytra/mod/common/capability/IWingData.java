package git.jbredwards.customizableelytra.mod.common.capability;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.item.ItemElytraWing;
import git.jbredwards.customizableelytra.mod.common.util.CustomizationType;
import git.jbredwards.customizableelytra.mod.common.util.ElytraWingData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
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
public interface IWingData extends INBTSerializable<NBTTagCompound>
{
    @CapabilityInject(IWingData.class)
    @Nonnull Capability<IWingData> CAPABILITY = null;

    @Nonnull ElytraWingData getData();
    void setData(@Nonnull ElytraWingData dataIn);

    @Nullable
    static IWingData get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() instanceof ItemElytraWing)
            event.addCapability(new ResourceLocation(Constants.MODID, "wing"), new CapabilityProvider<>(CAPABILITY));
    }

    final class Impl implements IWingData
    {
        ElytraWingData data = new ElytraWingData();

        @Nonnull
        @Override
        public ElytraWingData getData() { return data; }

        @Override
        public void setData(@Nonnull ElytraWingData dataIn) { data = dataIn; }

        @Nonnull
        @Override
        public NBTTagCompound serializeNBT() {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("Type", data.type.ordinal());
            nbt.setInteger("Color", data.color);

            return nbt;
        }

        @Override
        public void deserializeNBT(@Nonnull NBTTagCompound nbt) {
            data = new ElytraWingData();
            data.type = CustomizationType.deserialize(nbt.getInteger("Type"));
            data.color = nbt.getInteger("Color");
        }
    }

    enum Storage implements Capability.IStorage<IWingData>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IWingData> capability, @Nonnull IWingData instance, @Nullable EnumFacing side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(@Nonnull Capability<IWingData> capability, @Nonnull IWingData instance, @Nullable EnumFacing side, @Nullable NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) instance.deserializeNBT((NBTTagCompound)nbt);
        }
    }
}
