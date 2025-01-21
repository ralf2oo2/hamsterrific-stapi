package ralf2oo2.hamsterrific.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.model.item.ItemWithRenderer;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.opengl.GL11;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.client.HamsterrificClient;
import ralf2oo2.hamsterrific.client.render.entity.modelpart.BallModelPart;

public class HamsterBallItem extends TemplateItem{
    private BallModelPart modelHamsterBall = new BallModelPart( 0, 0);
    public HamsterBallItem(Identifier identifier) {
        super(identifier);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
        this.setMaxCount(1);
    }

    // TODO: replace with something that isn't deprecated

//    public void renderItemOnGui(ItemRenderer itemRenderer, TextRenderer textRenderer, TextureManager textureManager, int itemId, int damage, int textureIndex, int x, int y) {
//        Minecraft minecraft = HamsterrificClient.getMc();
//        minecraft.textureManager.bindTexture(minecraft.textureManager.getTextureId(BallModelPart.resource));
//
//        GL11.glPushMatrix();
//
//        GL11.glTranslatef((float)(x - 2), (float)(y + 3), -3.0F);
//        GL11.glScalef(10.0F, 10.0F, 10.0F);
//        GL11.glTranslatef(1.0F, 1.3F, 1.0F);
//        GL11.glScalef(1.0F, 1.0F, -1.0F);
//        GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
//        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
//
//        GL11.glDisable(GL11.GL_CULL_FACE);
//        this.modelHamsterBall.renderWithColor(damage, 0.0625f);
//        GL11.glEnable(GL11.GL_CULL_FACE);
//
//        GL11.glPopMatrix();
//    }
}
