package ralf2oo2.hamsterrific.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicItemRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.hamsterrific.event.init.ItemRegistry;

@Mixin(ArsenicItemRenderer.class)
public class ArsenicItemRendererMixin {
    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;DDDFF)V", at = @At("HEAD"))
    private void hamsterrific_preRender(ItemEntity d, double e, double f, double g, float h, float par6, CallbackInfo ci) {
        if(d != null && d.stack.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @Inject(method = "renderItemOnGui(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/client/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", at = @At("HEAD"))
    private void hamsterrific_preRenderGuiItem(TextRenderer textRenderer, TextureManager textureManager, ItemStack itemStack, int x, int y, CallbackInfo cid, CallbackInfo ci) {
        if(itemStack != null && itemStack.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;DDDFF)V", at = @At("TAIL"))
    private void hamsterrific_postRender(ItemEntity d, double e, double f, double g, float h, float par6, CallbackInfo ci) {
        if(d != null && d.stack.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glDisable(GL11.GL_NORMALIZE);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    @Inject(method = "renderItemOnGui(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/client/texture/TextureManager;Lnet/minecraft/item/ItemStack;IILorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", at = @At("TAIL"))
    private void hamsterrific_postRenderGuiItem(TextRenderer textRenderer, TextureManager textureManager, ItemStack itemStack, int x, int y, CallbackInfo cid, CallbackInfo ci) {
        if(itemStack != null && itemStack.itemId == ItemRegistry.hamsterBallItem.id){
            GL11.glDisable(GL11.GL_NORMALIZE);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
