package ralf2oo2.hamsterrific.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.client.render.model.json.JsonUnbakedModel;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.BakedModelRendererImpl;
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
    public int ballColor = 0;

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
            this.ball.color = ballColor;
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

    @Override
    public void animateModel(LivingEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);

        HamsterEntity hamsterEntity = (HamsterEntity)entity;

        isChild = hamsterEntity.isChild();
        isInBall = hamsterEntity.isInBall();
        ballColor = hamsterEntity.getBallColor();

        for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
            if (i < hamsterEntity.getFoodStackCount()) {
                this.hamsterCheekRight[i].visible = true;
            }
            else {
                this.hamsterCheekRight[i].visible = false;
            }
        }
        for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
            if (i < hamsterEntity.getFoodStackCount()) {
                this.hamsterCheekLeft[i].visible = true;
            }
            else {
                this.hamsterCheekLeft[i].visible = false;
            }
        }

        if (hamsterEntity.isHamsterSitting() | hamsterEntity.isRiding()) {
            this.hamsterBody.pitch = 1.0f;

            this.hamsterHead.setPivot(-1.5f, 16.0f, -1.5f);
            this.hamsterNose.setPivot(-1.5f, 16.0f, -1.5f);
            this.hamsterEarRight.setPivot(-1.5f, 16.0f, -1.5f);
            this.hamsterEarLeft.setPivot(-1.5f, 16.0f, -1.5f);

            for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
                this.hamsterCheekRight[i].setPivot(-1.5f, 16.0f, -1.5f);
            }
            for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
                this.hamsterCheekLeft[i].setPivot(-1.5f, 16.0f, -1.5f);
            }

            this.hamsterBody.setPivot(0.0f, 19.0f, 0.0f);
            this.hamsterLegBackRight.setPivot(-3.5f, 24.5f, 2.0f);
            this.hamsterLegBackLeft.setPivot(2.5f, 24.5f, 3.5f);

            this.hamsterLegBackRight.yaw = 0.8f;
            this.hamsterLegBackLeft.yaw = -0.8f;
            this.hamsterLegBackRight.pitch = -1.570796f;
            this.hamsterLegBackLeft.pitch = -1.570796f;
            this.hamsterLegFrontRight.pitch = 0.0f;
            this.hamsterLegFrontLeft.pitch = 0.0f;

            this.hamsterLegFrontRight.setPivot(-2.0f, 21.0f, -0.5f);
            this.hamsterLegFrontLeft.setPivot(2.0f, 21.0f, -0.5f);
            this.hamsterTail.setPivot(0.0f, 17.0f, 2.0f);
        }
        else if(hamsterEntity.isHamsterStanding()){
            this.hamsterBody.pitch = 0.0f;

            this.hamsterHead.setPivot(-1.5f, 10.0f, 4.5f);
            this.hamsterNose.setPivot(-1.5f, 10.0f, 4.5f);
            this.hamsterEarRight.setPivot(-1.5f, 10.0f, 4.5f);
            this.hamsterEarLeft.setPivot(-1.5f, 10.0f, 4.5f);

            for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
                this.hamsterCheekRight[i].setPivot(-1.5f, 10.0f, 4.5f);
            }
            for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
                this.hamsterCheekLeft[i].setPivot(-1.5f, 10.0f, 4.5f);
            }

            this.hamsterBody.setPivot(0.0f, 15.5f, 4.5f);
            this.hamsterBody.pitch = MathHelper.cos((float)Math.toRadians(80.0));

            this.hamsterLegBackRight.setPivot(-2.0f, 21.0f, 6.0f);
            this.hamsterLegBackLeft.setPivot(2.0f, 21.0f, 6.0f);
            this.hamsterLegFrontRight.setPivot(-2.0f, 15.0f, 3.0f);
            this.hamsterLegFrontLeft.setPivot(2.0f, 15.0f, 3.0f);

            this.hamsterLegBackRight.yaw = 0.0f;
            this.hamsterLegBackLeft.yaw = 0.0f;
            this.hamsterLegBackRight.pitch = MathHelper.cos(limbAngle * 1.5f) * 1.4f * limbDistance;
            this.hamsterLegBackLeft.pitch = MathHelper.cos(limbAngle * 1.5f + 3.141593f) * 1.4f * limbDistance;
            this.hamsterLegFrontRight.pitch = MathHelper.cos((float)Math.toRadians(150.0));
            this.hamsterLegFrontLeft.pivotX = MathHelper.cos((float)Math.toRadians(150.0));
            this.hamsterLegFrontRight.yaw = MathHelper.sin((float)Math.toRadians(-10.0));
            this.hamsterLegFrontLeft.yaw = MathHelper.sin((float)Math.toRadians(10.0));

            this.hamsterTail.setPivot(0.0f, 15.0f, 2.0f);
        }
        else{
            this.hamsterBody.pitch = 1.570796f;

            this.hamsterHead.setPivot(-1.5f, 16.0f, -2.0f);
            this.hamsterNose.setPivot(-1.5f, 16.0f, -2.0f);
            this.hamsterEarRight.setPivot(-1.5f, 16.0f, -2.0f);
            this.hamsterEarLeft.setPivot(-1.5f, 16.0f, -2.0f);

            for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
                this.hamsterCheekRight[i].setPivot(-1.5f, 16.0f, -2.0f);
            }
            for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
                this.hamsterCheekLeft[i].setPivot(-1.5f, 16.0f, -2.0f);
            }

            this.hamsterBody.setPivot(0.0f, 19.0f, 0.0f);
            this.hamsterLegBackRight.setPivot(-2.0f, 21.0f, 6.0f);
            this.hamsterLegBackLeft.setPivot(2.0f, 21.0f, 6.0f);

            this.hamsterLegBackRight.yaw = 0.0f;
            this.hamsterLegBackLeft.yaw = 0.0f;

            this.hamsterLegBackRight.pitch = MathHelper.cos(limbAngle * 1.5f) * 1.4f * limbDistance;
            this.hamsterLegBackLeft.pitch = MathHelper.cos(limbAngle * 1.5f + 3.141593f) * 1.4f * limbDistance;
            this.hamsterLegFrontRight.pitch = MathHelper.cos(limbAngle * 1.5f + 3.141593f) * 1.4f * limbDistance;
            this.hamsterLegFrontLeft.pitch = MathHelper.cos(limbAngle * 1.5f) * 1.4f * limbDistance;

            this.hamsterLegFrontRight.setPivot(-2.0f, 21.0f, -0.5f);
            this.hamsterLegFrontLeft.setPivot(2.0f, 21.0f, -0.5f);
            this.hamsterTail.setPivot(0.0f, 15.0f, 2.0f);
        }
        if(hamsterEntity.isHamsterTamed()){
            float f = hamsterEntity.age + tickDelta;
            this.hamsterTail.roll = MathHelper.sin(f * 3.141593f * 0.05f) * MathHelper.sin(f * 3.141593f * 11.0f * 0.05f) * 0.15f * 3.141593f;
        }
        else {
            this.hamsterTail.roll = 0;
        }
        float interestedAngle = hamsterEntity.getInterestedAngle(tickDelta);

        this.hamsterHead.roll = interestedAngle;
        this.hamsterNose.roll = interestedAngle;
        this.hamsterEarRight.roll = interestedAngle;
        this.hamsterEarLeft.roll = interestedAngle;

        for (int i = 0; i < this.hamsterCheekRight.length; ++i) {
            this.hamsterCheekRight[i].roll = interestedAngle;
        }
        for (int i = 0; i < this.hamsterCheekLeft.length; ++i) {
            this.hamsterCheekLeft[i].roll = interestedAngle;
        }
    }
}
