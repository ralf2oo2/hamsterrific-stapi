package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.registry.MobHandlerRegistry;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.entity.HamsterEntity;

public class EntityRegistry {
    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(HamsterEntity.class, Hamsterrific.NAMESPACE.id("hamster").toString());
    }

    @EventListener
    public void registerHandlers(MobHandlerRegistryEvent event) {
        event.register(Hamsterrific.NAMESPACE.id("hamster"), HamsterEntity::new);
    }
}
