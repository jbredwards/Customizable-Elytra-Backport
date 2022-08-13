package git.jbredwards.customizableelytra.mod.common.capability;

import git.jbredwards.customizableelytra.mod.Constants;
import net.minecraft.init.Items;
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
public interface IElytraData
{
    @CapabilityInject(IElytraData.class)
    @Nonnull Capability<IElytraData> CAPABILITY = null;

    @Nonnull IWingData getLeftWing();
    @Nonnull IWingData getRightWing();

    void setLeftWing(@Nonnull IWingData data);
    void setRightWing(@Nonnull IWingData data);

    @Nullable
    static IElytraData get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() == Items.ELYTRA)
            event.addCapability(new ResourceLocation(Constants.MODID, "elytra"), new CapabilityProvider<>(CAPABILITY));
    }

    final class Impl implements IElytraData
    {
        @Nonnull
        IWingData leftWingData = new IWingData.Impl(),
                rightWingData = new IWingData.Impl();

        @Nonnull
        @Override
        public IWingData getLeftWing() { return leftWingData; }

        @Nonnull
        @Override
        public IWingData getRightWing() { return rightWingData; }

        @Override
        public void setLeftWing(@Nonnull IWingData data) { leftWingData = data; }

        @Override
        public void setRightWing(@Nonnull IWingData data) { rightWingData = data; }
    }

    enum Storage implements Capability.IStorage<IElytraData>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IElytraData> capability, @Nonnull IElytraData instance, @Nullable EnumFacing side) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("LeftWing", instance.getLeftWing().serializeNBT());
            nbt.setTag("RightWing", instance.getRightWing().serializeNBT());
            return nbt;
        }

        @Override
        public void readNBT(@Nonnull Capability<IElytraData> capability, @Nonnull IElytraData instance, @Nullable EnumFacing side, @Nullable NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) {
                final IWingData leftWing = new IWingData.Impl();
                leftWing.deserializeNBT((NBTTagCompound)nbt);
                instance.setLeftWing(leftWing);

                final IWingData rightWing = new IWingData.Impl();
                rightWing.deserializeNBT((NBTTagCompound)nbt);
                instance.setRightWing(rightWing);
            }
        }
    }
}
