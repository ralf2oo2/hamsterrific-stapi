package ralf2oo2.hamsterrific.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class AgeableEntity extends MobEntity {
    public AgeableEntity(World world) {
        super(world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(12, 0);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Age", this.getGrowingAge());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        int age = nbt.getInt("Age");
        setGrowingAge(age);
    }
    @Override
    public void tickMovement() {
        super.tickMovement();

        int age = this.getGrowingAge();
        if(age < 0){
            age++;
            this.setGrowingAge(age);
        }
        if(age > 0){
            age--;
            this.setGrowingAge(age);
        }
    }

    public float getSoundPitch()
    {
        return this.isChild() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    public void setGrowingAge(int age)
    {
        this.dataTracker.set(12, age);
    }
    public int getGrowingAge()
    {
        return this.dataTracker.getInt(12);
    }

    public boolean isChild()
    {
        return this.getGrowingAge() < 0;
    }
}
