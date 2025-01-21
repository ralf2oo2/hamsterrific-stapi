package ralf2oo2.hamsterrific.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.render.model.ModelIdentifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

public class DebugItem extends TemplateItem {
    public DebugItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        HamsterEntity hamsterEntity = new HamsterEntity(world);
        hamsterEntity.setPositionAndAngles(x, y + 1, z, 0, 0);
        if(user.isSneaking()){
            hamsterEntity.setInBall(true);
            StationRenderAPI.getBakedModelManager().getModel(ModelIdentifier.of(Hamsterrific.NAMESPACE.id("e"), "block"));
        }

        world.spawnEntity(hamsterEntity);
        return true;
    }
}
