package ralf2oo2.hamsterrific.client.render.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;
import ralf2oo2.hamsterrific.block.entity.WaterBottleBlockEntity;
import ralf2oo2.hamsterrific.client.HamsterrificClient;

public class WaterBottleBlockEntityRenderer extends BlockEntityRenderer {
    public WaterBottleModel model = new WaterBottleModel();
    public static final String resource = "hamsterrific:textures/entity/waterbottle/waterbottle.png";

    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {
        Minecraft minecraft = HamsterrificClient.getMc();
        minecraft.textureManager.bindTexture(minecraft.textureManager.getTextureId(resource));

        float rotation = (((WaterBottleBlockEntity)blockEntity).rotation * 360) / 16.0f;

        GL11.glPushMatrix();

        GL11.glTranslatef((float)(x + 0.5f), (float) (y + 1.8f), (float)(z + 0.5f));
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        this.model.render(0.0625f);

        // TODO: determine if disabling this is necessary
        GL11.glDisable(GL11.GL_NORMALIZE);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }
}
