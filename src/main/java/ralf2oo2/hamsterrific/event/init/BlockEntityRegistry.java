package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.block.entity.WaterBottleBlockEntity;

public class BlockEntityRegistry {

    @EventListener
    public void registerTileEntities(BlockEntityRegisterEvent event)
    {
        event.register(WaterBottleBlockEntity.class, String.valueOf(Identifier.of(Hamsterrific.NAMESPACE, "water_bottle")));
    }
}
