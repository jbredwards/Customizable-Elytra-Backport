package git.jbredwards.customizableelytra.mod.common.compat;

import git.jbredwards.customizableelytra.mod.common.util.CustomizationType;
import git.jbredwards.customizableelytra.mod.common.util.ElytraWingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

/**
 * Changes the quark color nbt to this mod's format
 * @author jbred
 *
 */
public final class QuarkColorTagFix implements IFixableData
{
    public static final int DATA_VERSION = 101;

    @Override
    public int getFixVersion() { return DATA_VERSION; }

    @Nonnull
    @Override
    public NBTTagCompound fixTagCompound(@Nonnull NBTTagCompound compound) {
        if("minecraft:elytra".equals(compound.getString("id"))) {
            final NBTTagCompound tag = compound.getCompoundTag("tag");
            if(tag.hasKey("quark:elytraDye", Constants.NBT.TAG_INT)) {
                final ElytraWingData data = new ElytraWingData();
                data.color = tag.getInteger("quark:elytraDye");
                data.type = CustomizationType.DYE;

                final NBTTagCompound cap = new NBTTagCompound();
                cap.setTag("LeftWing", data.serializeNBT());
                cap.setTag("RightWing", data.serializeNBT());
                cap.setBoolean("AreWingsDuplicates", true);

                final NBTTagCompound caps = compound.getCompoundTag("ForgeCaps");
                caps.setTag("customizableelytra:elytra", cap);

                if(!compound.hasKey("ForgeCaps")) compound.setTag("ForgeCaps", caps);
            }
        }

        return compound;
    }
}