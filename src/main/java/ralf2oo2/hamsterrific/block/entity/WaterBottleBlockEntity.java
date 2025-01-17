package ralf2oo2.hamsterrific.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

public class WaterBottleBlockEntity extends BlockEntity {
    public int rotation;

    @Override
    public void readNbt(NbtCompound nbt) {
        this.rotation = nbt.getInt("rotation");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("rotation", this.rotation);
    }
}
