package git.jbredwards.customizableelytra.mod.client.layer;

import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class LayerCustomizableElytra implements LayerRenderer<AbstractClientPlayer>
{
    @Nonnull
    protected static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

    @Nonnull protected final RenderPlayer renderer;
    @Nonnull protected final ModelElytra modelElytra = new ModelElytra();

    public LayerCustomizableElytra(@Nonnull RenderPlayer renderer) { this.renderer = renderer; }

    @Override
    public void doRenderLayer(@Nonnull AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        final ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);

        if(cap != null) {
            renderWing(stack, cap.getLeftWing().getData(), modelElytra.rightWing, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            renderWing(stack, cap.getRightWing().getData(), modelElytra.leftWing, entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    protected void renderWing(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull ModelRenderer wingModelToIgnore, @Nonnull AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();

        //apply custom color
        boolean changedColor = false;
        for(int i = data.size() - 1; i >= 0; i--) {
            final EnumActionResult result = data.getCustomizationAt(i).changeEquipmentColor(stack, data, entity);
            if(result == EnumActionResult.PASS) continue;
            if(result == EnumActionResult.SUCCESS) changedColor = true;
            break;
        }

        //apply default color
        if(!changedColor) GlStateManager.color(1, 1, 1);

        //apply custom texture
        boolean changedTexture = false;
        for(int i = data.size() - 1; i >= 0; i--) {
            final EnumActionResult result = data.getCustomizationAt(i).changeTexture(stack, data, entity, renderer::bindTexture);
            if(result == EnumActionResult.PASS) continue;
            if(result == EnumActionResult.SUCCESS) changedTexture = true;
            break;
        }

        //apply default texture
        if(!changedTexture) {
            if(entity.isPlayerInfoSet() && entity.getLocationElytra() != null)
                renderer.bindTexture(entity.getLocationElytra());

            else if(entity.hasPlayerInfo() && entity.getLocationCape() != null && entity.isWearing(EnumPlayerModelParts.CAPE))
                renderer.bindTexture(entity.getLocationCape());

            else renderer.bindTexture(TEXTURE_ELYTRA);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0.125);

        final boolean prevIsHidden = wingModelToIgnore.isHidden;
        wingModelToIgnore.isHidden = true;

        modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        modelElytra.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if(stack.isItemEnchanted()) {
            setupQuarkGlint(stack);
            LayerArmorBase.renderEnchantedGlint(renderer, entity, modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }

        wingModelToIgnore.isHidden = prevIsHidden;

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() { return false; }

    //sets up the quark enchantment glint if it's installed
    protected void setupQuarkGlint(@Nonnull ItemStack stack) { }
}
