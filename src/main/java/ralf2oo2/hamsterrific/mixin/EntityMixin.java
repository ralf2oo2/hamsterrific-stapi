package ralf2oo2.hamsterrific.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

@Mixin(Entity.class)
public class EntityMixin{


    @Shadow public Entity passenger;

    @Inject(method = "getPassengerRidingHeight", at = @At("HEAD"), cancellable = true)
    void hamsterrific_modifyPlayerRidingHeight(CallbackInfoReturnable<Double> cir){
        if(PlayerEntity.class.isAssignableFrom(this.getClass())){
            if(passenger instanceof HamsterEntity){
                cir.setReturnValue(0.25D);
            }
        }
    }


}
