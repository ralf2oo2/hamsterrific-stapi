package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import ralf2oo2.hamsterrific.client.render.entity.HamsterEntityRenderer;
import ralf2oo2.hamsterrific.client.render.entity.model.HamsterEntityModel;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

public class EntityRendererRegistry {
    @EventListener
    public static void registerEntityRenderers(EntityRendererRegisterEvent event)
    {
        event.renderers.put(HamsterEntity.class, new HamsterEntityRenderer(new HamsterEntityModel(), 0));
    }
}
