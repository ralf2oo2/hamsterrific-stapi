package ralf2oo2.hamsterrific.client.render.block.entity;

import net.minecraft.client.model.ModelPart;

public class WaterBottleModel {
    ModelPart shape1;
    ModelPart shape2;
    ModelPart shape3;
    ModelPart shape4;

    public WaterBottleModel(){
        this.shape1 = new ModelPart(22, 0);
        this.shape1.addCuboid(0.0f, -2.5f, -0.5f, 1, 5, 1);
        this.shape1.setPivot(-0.5f, 20.0f, 4.0f);
        this.shape1.mirror = true;
        this.setRotation(this.shape1, -0.7853982f, 0.0f, 0.0f);

        this.shape2 = new ModelPart(0, 0);
        this.shape2.addCuboid(0.0f, 0.0f, 0.0f, 5, 10, 5);
        this.shape2.setPivot(-2.5f, 7.0f, 3.0f);
        this.shape2.mirror = true;
        this.setRotation(this.shape2, 0.0f, 0.0f, 0.0f);

        this.shape3 = new ModelPart(0, 16);
        this.shape3.addCuboid(0.0f, 0.0f, 0.0f, 3, 2, 3);
        this.shape3.setPivot(-1.5f, 17.0f, 4.0f);
        this.shape3.mirror = true;
        this.setRotation(this.shape3, 0.0f, 0.0f, 0.0f);

        this.shape4 = new ModelPart(33, 0);
        this.shape4.addCuboid(0.0f, 0.0f, 0.0f, 4, 9, 4);
        this.shape4.setPivot(-2.0f, 7.5f, 3.5f);
        this.shape4.mirror = true;
        this.setRotation(this.shape4, 0.0f, 0.0f, 0.0f);
    }

    private void setRotation(ModelPart model, float pitch, float yaw, float roll) {
        model.pitch = pitch;
        model.yaw = yaw;
        model.roll = roll;
    }

    public void render(float scale){
        this.shape1.render(scale);
        this.shape2.render(scale);
        this.shape3.render(scale);
        this.shape4.render(scale);
    }
}
