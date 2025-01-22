package ralf2oo2.hamsterrific.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.hamsterrific.event.init.ItemRegistry;

@Mixin(net.minecraft.client.render.item.HeldItemRenderer.class)
public class HeldItemRenderer {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void hamsterrific_preRenderItem(LivingEntity stack, ItemStack par2, CallbackInfo ci) {
        if(par2 != null && par2.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @Inject(method = "renderItem", at = @At("TAIL"))
    private void hamsterrific_postRenderItem(LivingEntity stack, ItemStack par2, CallbackInfo ci) {
        if(par2 != null && par2.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glDisable(GL11.GL_NORMALIZE);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
