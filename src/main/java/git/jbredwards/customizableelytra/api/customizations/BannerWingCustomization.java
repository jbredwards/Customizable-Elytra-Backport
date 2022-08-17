package git.jbredwards.customizableelytra.api.customizations;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author jbred
 *
 */
public final class BannerWingCustomization implements IWingCustomization
{
    @SideOnly(Side.CLIENT)
    @Nonnull public static final BannerTextures.Cache ELYTRA_DESIGNS = new BannerTextures.Cache("E", new ResourceLocation("customizableelytra:textures/entity/elytra.png"), "customizableelytra:textures/entity/elytra_banner/");

    @Nonnull NBTTagList patterns;
    @Nonnull EnumDyeColor bannerColor;

    @SideOnly(Side.CLIENT)
    boolean patternDataSet = false;

    @SideOnly(Side.CLIENT)
    @Nonnull List<BannerPattern> patternList = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    @Nonnull List<EnumDyeColor> colorList = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    @Nonnull String patternResourceLocation = "";

    public BannerWingCustomization() { this(new NBTTagList(), EnumDyeColor.BLACK); }
    public BannerWingCustomization(@Nonnull NBTTagList patternsIn, @Nonnull EnumDyeColor bannerColorIn) {
        patterns = new NBTTagList();
        bannerColor = bannerColorIn;

        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("Pattern", BannerPattern.BASE.getHashname());
        nbt.setInteger("Color", bannerColor.getDyeDamage());

        patterns.appendTag(nbt);
        patternsIn.forEach(patterns::appendTag);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addTooltip(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull List<String> tooltip, boolean advanced) {
        for(int i = 0; i < patterns.tagCount() && i < 6; ++i) {
            final EnumDyeColor color = getColorList().get(i);
            final BannerPattern pattern = getPatternList().get(i);

            tooltip.add(I18n.format(
                String.format("item.banner.%s.%s", pattern.getFileName(), color.getTranslationKey())
            ));
        }
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public EnumActionResult changeTexture(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, @Nonnull Consumer<ResourceLocation> textureBinder) {
        final @Nullable ResourceLocation texture = ELYTRA_DESIGNS.getResourceLocation(getPatternResourceLocation(), getPatternList(), getColorList());
        if(texture != null) {
            textureBinder.accept(texture);
            return EnumActionResult.SUCCESS;
        }

        //should never pass
        return EnumActionResult.PASS;
    }

    @Override
    public void writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setInteger("Base", bannerColor.getDyeDamage());
        if(!patterns.isEmpty()) compound.setTag("Patterns", patterns);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        bannerColor = EnumDyeColor.byDyeDamage(compound.getInteger("Base"));

        if(compound.hasKey("Patterns", Constants.NBT.TAG_LIST))
            patterns = compound.getTagList("Patterns", Constants.NBT.TAG_COMPOUND);
    }

    @Override
    public boolean onWash(@Nonnull WingCustomizationData data) {
        if(patterns.tagCount() <= 1) data.removeLast();
        else patterns.removeTag(patterns.tagCount() - 1);
        return true;
    }

    @Override
    public void onAddedToData(@Nonnull WingCustomizationData data) {
        data.baseColor = bannerColor.colorValue;
    }

    @Override
    public void onDataRemoved(@Nonnull WingCustomizationData data, boolean removed) {
        if(removed) data.baseColor = -1;
        else if(data.baseColor == -1) data.baseColor = bannerColor.colorValue;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public List<BannerPattern> getPatternList() {
        initializeBannerData();
        return patternList;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public List<EnumDyeColor> getColorList() {
        initializeBannerData();
        return colorList;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public String getPatternResourceLocation() {
        initializeBannerData();
        return patternResourceLocation;
    }

    @SideOnly(Side.CLIENT)
    void initializeBannerData() {
        if(!patternDataSet) {
            patternList.add(BannerPattern.BASE);
            colorList.add(bannerColor);
            patternResourceLocation = "b" + bannerColor.getDyeDamage();
            for(int i = 1; i < patterns.tagCount(); i++) {
                final NBTTagCompound nbt = patterns.getCompoundTagAt(i);
                final @Nullable BannerPattern pattern = BannerPattern.byHash(nbt.getString("Pattern"));

                if(pattern != null) {
                    final int color = nbt.getInteger("Color");

                    patternList.add(pattern);
                    colorList.add(EnumDyeColor.byDyeDamage(color));

                    patternResourceLocation += pattern.getHashname() + color;
                }
            }

            patternDataSet = true;
        }
    }
}
