package ralf2oo2.hamsterrific.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;
import ralf2oo2.hamsterrific.client.render.entity.modelpart.BallModelPart;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

public class HamsterEntityModel extends EntityModel {
    public ModelPart hamsterHead;
    public ModelPart hamsterBody;
    public ModelPart hamsterLegBackRight;
    public ModelPart hamsterLegBackLeft;
    public ModelPart hamsterLegFrontRight;
    public ModelPart hamsterLegFrontLeft;
    public ModelPart hamsterNose;
    public ModelPart hamsterEarRight;
    public ModelPart hamsterEarLeft;
    public ModelPart hamsterTail;
    public ModelPart[] hamsterCheekRight;
    public ModelPart[] hamsterCheekLeft;
    public BallModelPart ball;

    public boolean isChild = false;
    public boolean isInBall = false;

    public HamsterEntityModel(){
        this.hamsterHead = new ModelPart(0, 0);
        this.hamsterHead.addCuboid(-2.5f, -1.5f, -5.0f, 5, 5, 5, 0.5f);
        this.hamsterHead.setPivot(0.0f, 16.0f, -2.0f);
        this.hamsterNose = new ModelPart(0, 25);
        this.hamsterNose.addCuboid(-1.5f, 1.5f, -6.0f, 3, 2, 1, 0.5f);
        this.hamsterNose.setPivot(0.0f, 16.0f, -2.0f);
        this.hamsterEarRight = new ModelPart(10, 15);
        this.hamsterEarRight.addCuboid(-2.5f, -3.0f, -4.5f, 1, 1, 1, 0.5f);
        this.hamsterEarRight.setPivot(0.0f, 16.0f, -2.0f);
        this.hamsterEarLeft = new ModelPart( 10, 18);
        this.hamsterEarLeft.addCuboid(1.5f, -3.0f, -4.5f, 1, 1, 1, 0.5f);
        this.hamsterEarLeft.setPivot(0.0f, 16.0f, -2.0f);
        this.hamsterCheekRight = new ModelPart[5];
        for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
            this.hamsterCheekRight[i] = new ModelPart(10, 21);
            this.hamsterCheekRight[i].addCuboid(-3.5f, 1.5f, -2.5f, 1, 1, 1, (float)i * 0.4f);
            this.hamsterCheekRight[i].setPivot(0.0f, 16.0f, -2.0f);
        }
        this.hamsterCheekLeft = new ModelPart[5];
        for (int j = 0; j < this.hamsterCheekLeft.length; ++j) {
            this.hamsterCheekLeft[j] = new ModelPart(10, 24);
            this.hamsterCheekLeft[j].addCuboid(2.5f, 1.5f, -2.5f, 1, 1, 1, (float)j * 0.4f);
            this.hamsterCheekLeft[j].setPivot(0.0f, 16.0f, -2.0f);
        }
        this.hamsterBody = new ModelPart(28, 8);
        this.hamsterBody.addCuboid(-4.0f, -3.0f, -2.0f, 5, 8, 5, 0.5f);
        this.hamsterBody.setPivot(0.0f, 19.0f, 0.0f);
        this.hamsterTail = new ModelPart(10, 15);
        this.hamsterTail.addCuboid(-2.0f, 4.0f, -4.0f, 1, 1, 1, 0.5f);
        this.hamsterTail.setPivot(0.0f, 15.0f, 2.0f);
        this.hamsterLegBackRight = new ModelPart(0, 16);
        this.hamsterLegBackRight.addCuboid(-2.0f, 0.0f, -2.0f, 1, 2, 1, 0.5f);
        this.hamsterLegBackRight.setPivot(-2.0f, 21.0f, 6.0f);
        this.hamsterLegBackLeft = new ModelPart(0, 16);
        this.hamsterLegBackLeft.addCuboid(-2.0f, 0.0f, -2.0f, 1, 2, 1, 0.5f);
        this.hamsterLegBackLeft.setPivot(2.0f, 21.0f, 6.0f);
        this.hamsterLegFrontRight = new ModelPart(0, 16);
        this.hamsterLegFrontRight.addCuboid(-2.0f, 0.0f, -2.0f, 1, 2, 1, 0.5f);
        this.hamsterLegFrontRight.setPivot(-2.0f, 21.0f, -0.5f);
        this.hamsterLegFrontLeft = new ModelPart(0, 16);
        this.hamsterLegFrontLeft.addCuboid(-2.0f, 0.0f, -2.0f, 1, 2, 1, 0.5f);
        this.hamsterLegFrontLeft.setPivot(2.0f, 21.0f, -0.5f);
        this.ball = new BallModelPart( 0, 0);
    }
    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        this.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);


        if(isChild){
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
        }

        this.hamsterHead.renderForceTransform(scale);
        this.hamsterNose.renderForceTransform(scale);
        this.hamsterEarRight.renderForceTransform(scale);
        this.hamsterEarLeft.renderForceTransform(scale);

        for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
            this.hamsterCheekRight[i].renderForceTransform(scale);
        }
        for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
            this.hamsterCheekLeft[i].renderForceTransform(scale);
        }
        this.hamsterBody.renderForceTransform(scale);

        this.hamsterTail.render(scale);
        this.hamsterLegBackRight.render(scale);
        this.hamsterLegBackLeft.render(scale);
        this.hamsterLegFrontRight.render(scale);
        this.hamsterLegFrontLeft.render(scale);

        if(isChild){
            GL11.glPopMatrix();
        }


        if(isInBall){
            //this.ball.color = entity.getBallColor();
            this.ball.render(scale);
        }
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        this.ball.rotation = (int)limbAngle;

        this.hamsterHead.pitch = headPitch / 57.29578f;
        this.hamsterHead.yaw = headYaw / 57.29578f;
        this.hamsterNose.pitch = this.hamsterHead.pitch;
        this.hamsterNose.yaw = this.hamsterHead.yaw;
        this.hamsterEarRight.pitch = this.hamsterHead.pitch;
        this.hamsterEarRight.yaw = this.hamsterHead.yaw;
        this.hamsterEarLeft.pitch = this.hamsterHead.pitch;
        this.hamsterEarLeft.yaw = this.hamsterHead.yaw;

        for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
            this.hamsterCheekRight[i].pitch = this.hamsterHead.pitch;
            this.hamsterCheekRight[i].yaw = this.hamsterHead.yaw;
        }
        for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
            this.hamsterCheekLeft[i].pitch = this.hamsterHead.pitch;
            this.hamsterCheekLeft[i].yaw = this.hamsterHead.yaw;
        }

        this.hamsterTail.pitch = 1.570796f;
    }

    // TODO: implement animation
    @Override
    public void animateModel(LivingEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        HamsterEntity hamsterEntity = (HamsterEntity)entity;

        // TODO: check if hamster is child
        isChild = false;
        // TODO: check if hamster is in ball
        isInBall = false;


    }
}
