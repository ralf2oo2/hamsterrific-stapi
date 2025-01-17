package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import ralf2oo2.hamsterrific.block.entity.WaterBottleBlockEntity;
import ralf2oo2.hamsterrific.client.render.block.entity.WaterBottleBlockEntityRenderer;

public class BlockEntityRendererRegistry {
    @EventListener
    public void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event)
    {
        event.renderers.put(WaterBottleBlockEntity.class, new WaterBottleBlockEntityRenderer());
    }
}
