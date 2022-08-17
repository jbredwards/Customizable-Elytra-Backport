package git.jbredwards.customizableelytra.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Adds quark compatibility
 * @author jbred
 *
 */
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Customizable Elytra Plugin")
public final class ASMHandler implements IFMLLoadingPlugin, Opcodes
{
    /**
     * This class exists because the launcher don't allow {@link IClassTransformer IClassTransformers}
     * to be the same class as {@link IFMLLoadingPlugin IFMLLoadingPlugins}
     */
    @SuppressWarnings("unused")
    public static final class Transformer implements IClassTransformer
    {
        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            final boolean isDuplicationRecipe = "vazkii.quark.misc.recipe.ElytraDuplicationRecipe".equals(transformedName);
            final boolean isDyeableElytra     = "vazkii.quark.vanity.feature.DyableElytra".equals(transformedName);

            if(isDuplicationRecipe || isDyeableElytra) {
                final ClassNode classNode = new ClassNode();
                new ClassReader(basicClass).accept(classNode, 0);

                if(isDyeableElytra) classNode.methods.removeIf(method -> method.name.equals("preInit") || method.name.equals("postInitClient"));
                else {
                    all:
                    for(MethodNode method : classNode.methods) {
                        if(method.name.equals("getRecipeOutput")) {
                            for(AbstractInsnNode insn : method.instructions.toArray()) {
                                if(insn.getOpcode() == INVOKESTATIC && ((MethodInsnNode)insn).owner.equals("vazkii/arl/util/ItemNBTHelper")) {
                                    ((MethodInsnNode)insn).owner = "git/jbredwards/customizableelytra/asm/QuarkASMHooks";
                                    break all;
                                }
                            }
                        }
                    }
                }

                //writes the changes
                final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                classNode.accept(writer);
                return writer.toByteArray();
            }

            return basicClass;
        }
    }

    @Nonnull
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"git.jbredwards.customizableelytra.asm.ASMHandler$Transformer"};
    }

    @Nullable
    @Override
    public String getModContainerClass() { return null; }

    @Nullable
    @Override
    public String getSetupClass() { return null; }

    @Override
    public void injectData(@Nonnull Map<String, Object> map) { }

    @Nullable
    @Override
    public String getAccessTransformerClass() { return null; }
}
