package git.jbredwards.customizableelytra.mod.common.capability;

import git.jbredwards.customizableelytra.mod.Constants;
import git.jbredwards.customizableelytra.mod.common.init.ModItems;
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
public interface IElytraCapability
{
    @CapabilityInject(IElytraCapability.class)
    @Nonnull Capability<IElytraCapability> CAPABILITY = null;

    @Nonnull IWingCapability getLeftWing();
    @Nonnull IWingCapability getRightWing();
    /**
     * Used to determine whether to render the wing tooltips as separate or not
     */
    boolean areWingsDuplicates();

    void setLeftWing(@Nonnull IWingCapability data);
    void setRightWing(@Nonnull IWingCapability data);
    void setAreWingsDuplicates(boolean flag);

    @Nonnull
    default Item getWingItem() { return ModItems.WING; }

    @Nullable
    static IElytraCapability get(@Nullable ICapabilityProvider provider) {
        return provider.hasCapability(CAPABILITY, null) ? provider.getCapability(CAPABILITY, null) : null;
    }

    @SubscribeEvent
    static void attach(@Nonnull AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() == Items.ELYTRA)
            event.addCapability(new ResourceLocation(Constants.MODID, "elytra"),
                    new CapabilityProvider<>(() -> CAPABILITY, new Impl()));
    }

    final class Impl implements IElytraCapability
    {
        @Nonnull IWingCapability leftWingData = new IWingCapability.Impl();
        @Nonnull IWingCapability rightWingData = new IWingCapability.Impl();
        boolean areWingsDuplicates = true;

        @Nonnull
        @Override
        public IWingCapability getLeftWing() { return leftWingData; }

        @Nonnull
        @Override
        public IWingCapability getRightWing() { return rightWingData; }

        @Override
        public boolean areWingsDuplicates() { return areWingsDuplicates; }

        @Override
        public void setLeftWing(@Nonnull IWingCapability data) { leftWingData = data; }

        @Override
        public void setRightWing(@Nonnull IWingCapability data) { rightWingData = data; }

        @Override
        public void setAreWingsDuplicates(boolean flag) { areWingsDuplicates = flag; }
    }

    enum Storage implements Capability.IStorage<IElytraCapability>
    {
        INSTANCE;

        @Nonnull
        @Override
        public NBTBase writeNBT(@Nonnull Capability<IElytraCapability> capability, @Nonnull IElytraCapability instance, @Nullable EnumFacing side) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("LeftWing", instance.getLeftWing().getData().serializeNBT());
            nbt.setTag("RightWing", instance.getRightWing().getData().serializeNBT());
            nbt.setBoolean("AreWingsDuplicates", instance.areWingsDuplicates());
            return nbt;
        }

        @Override
        public void readNBT(@Nonnull Capability<IElytraCapability> capability, @Nonnull IElytraCapability instance, @Nullable EnumFacing side, @Nullable NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) {
                final IWingCapability leftWing = new IWingCapability.Impl();
                leftWing.getData().deserializeNBT(((NBTTagCompound)nbt).getCompoundTag("LeftWing"));
                instance.setLeftWing(leftWing);

                final IWingCapability rightWing = new IWingCapability.Impl();
                rightWing.getData().deserializeNBT(((NBTTagCompound)nbt).getCompoundTag("RightWing"));
                instance.setRightWing(rightWing);

                instance.setAreWingsDuplicates(((NBTTagCompound)nbt).getBoolean("AreWingsDuplicates"));
            }
        }
    }
}
