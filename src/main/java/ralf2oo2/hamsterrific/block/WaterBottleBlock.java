package ralf2oo2.hamsterrific.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.hamsterrific.block.entity.WaterBottleBlockEntity;

public class WaterBottleBlock extends TemplateBlockWithEntity {
    public WaterBottleBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new WaterBottleBlockEntity();
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        super.onPlaced(world, x, y, z, placer);
        WaterBottleBlockEntity blockEntity = (WaterBottleBlockEntity)world.getBlockEntity(x, y, z);
        blockEntity.rotation = MathHelper.floor((placer.yaw * 16.0f / 360.0f) + 0.5) & 0xF;
    }
}
