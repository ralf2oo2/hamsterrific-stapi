package ralf2oo2.hamsterrific.client.render.entity;

import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ralf2oo2.hamsterrific.client.render.entity.model.HamsterEntityModel;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

public class HamsterEntityRenderer extends LivingEntityRenderer {
    private float scale;
    private HamsterEntityModel hamsterEntityModel;

    public HamsterEntityRenderer(EntityModel entityModel, float shadowRadius) {
        super(entityModel, shadowRadius);
        this.hamsterEntityModel = (HamsterEntityModel) entityModel;
        this.scale = 0.5f;
        this.shadowRadius = shadowRadius;
    }

    @Override
    protected void applyScale(LivingEntity entity, float scale) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    @Override
    protected void renderMore(LivingEntity entity, float tickDelta) {
        ItemStack itemStack = entity.getHeldItem();
        if(itemStack != null){
            GL11.glPushMatrix();

            this.hamsterEntityModel.hamsterLegFrontRight.transform(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);

            // TODO: confirm that this code works properly
            if(itemStack.getItem() instanceof BlockItem){
                // could also be replaced with 0.375
                float scale = 0.5f;
                GL11.glTranslatef(0.0f, 0.01875f, -0.3125f);
                scale *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(scale, -scale, scale);
            }
            if(itemStack.getItem().isHandheld()){
                GL11.glTranslatef(0.0f, 0.01875f, 0.0f);
                GL11.glScalef(0.625f, -0.625f, 0.625f);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GL11.glTranslatef(0.25f, 0.01875f, -0.1875f);
                GL11.glScalef(0.375f, 0.375f, 0.375f);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }

            this.dispatcher.heldItemRenderer.renderItem(entity, itemStack);

            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {
        HamsterEntity hamsterEntity = (HamsterEntity) entity;
        this.shadowRadius = hamsterEntity.getShadowRadius();
        if(hamsterEntity.isChild()){
            y -= 0.40;
        }
        if(hamsterEntity.vehicle != null){
            if(false) { // TODO: hamsterEntity.isRidingPlayer()
                --y;
            }
            else {
                float height = 0f; // TODO: hamsterEntity.isRidingSpecial();
                if (height > 0.0f && height >= 0.5f) {
                    if (height < 1.0f) {
                        y -= 0.2;
                    }
                    else if (height < 1.5f) {
                        y += 0.2;
                    }
                    else if (height < 2.0f) {
                        y += 0.5;
                    }
                    else if (height < 2.5f) {
                        y += 0.8;
                    }
                    else if (height < 3.0f) {
                        y += 1.05;
                    }
                    else {
                        y += 1.5;
                    }
                }
            }
        }

        // TODO: replace this with right hamster texture
        this.bindTexture("assets/hamsterrific/textures/entity/hamster/hamster_black.png");

        super.render(hamsterEntity, x, y, z, yaw, pitch);
    }
}
