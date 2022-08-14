package git.jbredwards.customizableelytra.mod.client.layer;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 *
 * @author jbred
 *
 */
@SideOnly(Side.CLIENT)
public class LayerCustomizableElytra implements LayerRenderer<AbstractClientPlayer>
{
    @Nonnull
    protected final RenderPlayer renderer;
    public LayerCustomizableElytra(@Nonnull RenderPlayer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(@Nonnull AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

    }

    @Override
    public boolean shouldCombineTextures() { return false; }
}
