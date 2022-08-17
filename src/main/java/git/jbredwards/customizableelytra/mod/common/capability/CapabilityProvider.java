package git.jbredwards.customizableelytra.mod.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * A general purpose capability provider class
 * @author jbred
 *
 */
public final class CapabilityProvider<T> implements ICapabilitySerializable<NBTBase>
{
    @Nonnull final Supplier<Capability<T>> capability;
    @Nonnull final T instance;

    public CapabilityProvider(@Nonnull Supplier<Capability<T>> capabilityIn, @Nonnull T instanceIn) {
        capability = capabilityIn;
        instance = instanceIn;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capabilityIn, @Nullable EnumFacing facing) {
        return capabilityIn == capability.get();
    }

    @Nullable
    @Override
    public <t> t getCapability(@Nonnull Capability<t> capabilityIn, @Nullable EnumFacing facing) {
        return capabilityIn == capability.get() ? capability.get().cast(instance) : null;
    }

    @Nullable
    @Override
    public NBTBase serializeNBT() { return capability.get().writeNBT(instance, null); }

    @Override
    public void deserializeNBT(@Nonnull NBTBase nbt) { capability.get().readNBT(instance, null, nbt); }
}
