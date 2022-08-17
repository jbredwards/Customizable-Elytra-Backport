package git.jbredwards.customizableelytra.mod.client.layer;

import git.jbredwards.customizableelytra.api.IWingCustomization;
import git.jbredwards.customizableelytra.mod.common.capability.IElytraCapability;
import git.jbredwards.customizableelytra.api.WingCustomizationData;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHandSide;
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
public class LayerCustomizableElytra implements LayerRenderer<EntityLivingBase>
{
    @Nonnull protected static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    @Nonnull protected final RenderLivingBase<?> renderer;

    public LayerCustomizableElytra(@Nonnull RenderLivingBase<?> renderer) { this.renderer = renderer; }

    @Override
    public void doRenderLayer(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        final ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        final @Nullable IElytraCapability cap = IElytraCapability.get(stack);

        if(cap != null) {
            renderWing(stack, cap.getLeftWing().getData(), entity, EnumHandSide.LEFT, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            renderWing(stack, cap.getRightWing().getData(), entity, EnumHandSide.RIGHT, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    protected void renderWing(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();

        //apply custom color
        boolean changedColor = false;
        for(int i = data.size() - 1; i >= 0; i--) {
            final IWingCustomization customization = data.getCustomizationAt(i);
            if(customization.isValid(data)) {
                final EnumActionResult result = customization.changeEquipmentColor(stack, data, entity, wing);
                if(result == EnumActionResult.PASS) continue;
                if(result == EnumActionResult.SUCCESS) changedColor = true;
                break;
            }
        }

        //apply default color
        if(!changedColor) GlStateManager.color(142f/255, 142f/255, 164f/255);

        //apply custom texture
        boolean changedTexture = false;
        for(int i = data.size() - 1; i >= 0; i--) {
            final IWingCustomization customization = data.getCustomizationAt(i);
            if(customization.isValid(data)) {
                final EnumActionResult result = customization.changeTexture(stack, data, entity, wing, renderer::bindTexture);
                if(result == EnumActionResult.PASS) continue;
                if(result == EnumActionResult.SUCCESS) changedTexture = true;
                break;
            }
        }

        //apply default texture
        if(!changedTexture) {
            if(entity instanceof AbstractClientPlayer) {
                final AbstractClientPlayer player = (AbstractClientPlayer)entity;
                if(player.isPlayerInfoSet() && player.getLocationElytra() != null)
                    renderer.bindTexture(player.getLocationElytra());

                else if(player.hasPlayerInfo() && player.getLocationCape() != null && player.isWearing(EnumPlayerModelParts.CAPE))
                    renderer.bindTexture(player.getLocationCape());

                else renderer.bindTexture(TEXTURE_ELYTRA);
            }

            else renderer.bindTexture(TEXTURE_ELYTRA);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0.125);

        //run pre-renderers
        for(int i = data.size() - 1; i >= 0; i--) {
            final IWingCustomization customization = data.getCustomizationAt(i);
            if(customization.isValid(data)) customization
                    .preRender(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }

        //run model renderers
        renderModel(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);

        //run post-renderers
        for(int i = data.size() - 1; i >= 0; i--) {
            final IWingCustomization customization = data.getCustomizationAt(i);
            if(customization.isValid(data)) customization
                    .postRender(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }

        GlStateManager.popMatrix();
        GlStateManager.color(1, 1, 1);
        GlStateManager.popMatrix();
    }

    public static void renderModel(@Nonnull ItemStack stack, @Nonnull WingCustomizationData data, @Nonnull RenderLivingBase<?> renderer, @Nonnull EntityLivingBase entity, @Nonnull EnumHandSide wing, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        //let customizations render custom models
        boolean changedModel = false;
        for(int i = data.size() - 1; i >= 0; i--) {
            final IWingCustomization customization = data.getCustomizationAt(i);
            if(customization.isValid(data)) {
                final EnumActionResult result = customization.renderModel(stack, data, renderer, entity, wing, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                if(result == EnumActionResult.PASS) continue;
                if(result == EnumActionResult.SUCCESS) changedModel = true;
                break;
            }
        }

        //default model rendering
        if(!changedModel) {
            final ModelElytra model = new ModelElytra();
            final ModelRenderer wingModelToIgnore = (wing == EnumHandSide.LEFT ? model.rightWing : model.leftWing);
            wingModelToIgnore.isHidden = true;

            model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if(stack.isItemEnchanted()) {
                setupQuarkGlint(stack);
                LayerArmorBase.renderEnchantedGlint(renderer, entity, model, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() { return false; }

    //sets up the quark enchantment glint if it's installed
    public static void setupQuarkGlint(@Nonnull ItemStack stack) { }
}
