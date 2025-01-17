package ralf2oo2.hamsterrific.client.render.entity.modelpart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.SheepEntity;
import org.lwjgl.opengl.GL11;
import ralf2oo2.hamsterrific.client.HamsterrificClient;

public class BallModelPart extends ModelPart {
    public static final String resource = "hamsterrific:textures/entity/ball/hamsterball.png";

    ModelPart Shape1;
    ModelPart Shape2;
    ModelPart Shape3;
    ModelPart Shape4;
    ModelPart Shape5;
    ModelPart Shape6;
    ModelPart Shape7;
    ModelPart Shape8;
    ModelPart Shape9;
    ModelPart Shape10;
    ModelPart Shape11;
    ModelPart Shape12;
    ModelPart Shape13;
    ModelPart Shape14;
    ModelPart Shape15;
    ModelPart Shape16;

    public int rotation = 0;
    public int color = 0;

    public BallModelPart(int u, int v) {
        super(u, v);

        this.Shape1 = new ModelPart(0, 0);
        this.Shape1.addCuboid(-5.0f, 4.0f, -5.0f, 10, 1, 10);
        this.Shape1.setPivot(0.0f, 17.0f, 0.0f);
        this.Shape1.mirror = true;
        this.setRotation(this.Shape1, 0.0f, 0.0f, 0.0f);

        this.Shape2 = new ModelPart(0, 0);
        this.Shape2.addCuboid(-5.0f, -6.0f, -5.0f, 10, 1, 10);
        this.Shape2.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape2.mirror = true;
        this.setRotation(this.Shape2, 0.0f, 0.0f, 0.0f);

        this.Shape3 = new ModelPart(0, 0);
        this.Shape3.addCuboid(-5.0f, -5.0f, -5.0f, 1, 8, 10);
        this.Shape3.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape3.mirror = true;
        this.setRotation(this.Shape3, 0.0f, 0.0f, 0.0f);

        this.Shape4 = new ModelPart(0, 0);
        this.Shape4.addCuboid(4.0f, -5.0f, -5.0f, 1, 8, 10);
        this.Shape4.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape4.mirror = true;
        this.setRotation(this.Shape4, 0.0f, 0.0f, 0.0f);

        this.Shape5 = new ModelPart(0, 0);
        this.Shape5.addCuboid(-4.0f, -5.0f, -6.0f, 8, 8, 2);
        this.Shape5.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape5.mirror = true;
        this.setRotation(this.Shape5, 0.0f, 0.0f, 0.0f);

        this.Shape6 = new ModelPart(0, 0);
        this.Shape6.addCuboid(-3.0f, -4.0f, 6.0f, 6, 6, 1);
        this.Shape6.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape6.mirror = true;
        this.setRotation(this.Shape6, 0.0f, 0.0f, 0.0f);

        this.Shape7 = new ModelPart(0, 0);
        this.Shape7.addCuboid(5.0f, -5.0f, -4.0f, 1, 8, 8);
        this.Shape7.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape7.mirror = true;
        this.setRotation(this.Shape7, 0.0f, 0.0f, 0.0f);

        this.Shape8 = new ModelPart(0, 0);
        this.Shape8.addCuboid(-6.0f, -5.0f, -4.0f, 1, 8, 8);
        this.Shape8.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape8.mirror = true;
        this.setRotation(this.Shape8, 0.0f, 0.0f, 0.0f);

        this.Shape9 = new ModelPart(0, 0);
        this.Shape9.addCuboid(-4.0f, -7.0f, -4.0f, 8, 1, 8);
        this.Shape9.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape9.mirror = true;
        this.setRotation(this.Shape9, 0.0f, 0.0f, 0.0f);

        this.Shape10 = new ModelPart(0, 0);
        this.Shape10.addCuboid(-4.0f, 5.0f, -4.0f, 8, 1, 8);
        this.Shape10.setPivot(0.0f, 17.0f, 0.0f);
        this.Shape10.mirror = true;
        this.setRotation(this.Shape10, 0.0f, 0.0f, 0.0f);

        this.Shape11 = new ModelPart(0, 0);
        this.Shape11.addCuboid(-3.0f, -4.0f, -7.0f, 6, 6, 1);
        this.Shape11.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape11.mirror = true;
        this.setRotation(this.Shape11, 0.0f, 0.0f, 0.0f);

        this.Shape12 = new ModelPart(0, 0);
        this.Shape12.addCuboid(-7.0f, -4.0f, -3.0f, 1, 6, 6);
        this.Shape12.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape12.mirror = true;
        this.setRotation(this.Shape12, 0.0f, 0.0f, 0.0f);

        this.Shape13 = new ModelPart(0, 0);
        this.Shape13.addCuboid(-4.0f, -5.0f, 4.0f, 8, 8, 2);
        this.Shape13.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape13.mirror = true;
        this.setRotation(this.Shape13, 0.0f, 0.0f, 0.0f);

        this.Shape14 = new ModelPart(0, 0);
        this.Shape14.addCuboid(6.0f, -4.0f, -3.0f, 1, 6, 6);
        this.Shape14.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape14.mirror = true;
        this.setRotation(this.Shape14, 0.0f, 0.0f, 0.0f);

        this.Shape15 = new ModelPart(0, 0);
        this.Shape15.addCuboid(-3.0f, -8.0f, -3.0f, 6, 1, 6);
        this.Shape15.setPivot(0.0f, 18.0f, 0.0f);
        this.Shape15.mirror = true;
        this.setRotation(this.Shape15, 0.0f, 0.0f, 0.0f);

        this.Shape16 = new ModelPart(0, 0);
        this.Shape16.addCuboid(-3.0f, 6.0f, -3.0f, 6, 1, 6);
        this.Shape16.setPivot(0.0f, 17.0f, 0.0f);
        this.Shape16.mirror = true;
        this.setRotation(this.Shape16, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void render(float scale) {
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Minecraft minecraft = HamsterrificClient.getMc();
        minecraft.textureManager.bindTexture(minecraft.textureManager.getTextureId(resource));

        float[] color = SheepEntity.COLORS[this.color];
        GL11.glColor3f(color[0], color[1], color[2]);

        GL11.glPushMatrix();

        GL11.glTranslatef(0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)(this.rotation * 20), 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.9f, 0.0f);
        GL11.glScalef(1.7f, 1.7f, 1.7f);

        this.Shape1.render(scale);
        this.Shape2.render(scale);
        this.Shape3.render(scale);
        this.Shape4.render(scale);
        this.Shape5.render(scale);
        this.Shape6.render(scale);
        this.Shape7.render(scale);
        this.Shape8.render(scale);
        this.Shape9.render(scale);
        this.Shape10.render(scale);
        this.Shape11.render(scale);
        this.Shape12.render(scale);
        this.Shape13.render(scale);
        this.Shape14.render(scale);
        this.Shape15.render(scale);
        this.Shape16.render(scale);

        GL11.glPopMatrix();

        // TODO: determine if disabling this is necessary
        GL11.glDisable(GL11.GL_NORMALIZE);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void renderWithColor(int color, float scale){
        this.color = color;
        render(scale);
    }

    private void setRotation(ModelPart model, float pitch, float yaw, float roll) {
        model.pitch = pitch;
        model.yaw = yaw;
        model.roll = roll;
    }
}
