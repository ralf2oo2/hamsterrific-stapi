package ralf2oo2.hamsterrific.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.modificationstation.stationapi.api.client.StationRenderAPI;
import net.modificationstation.stationapi.api.client.render.model.ModelIdentifier;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import ralf2oo2.hamsterrific.Hamsterrific;

public class HamsterrificClient {
    public HamsterrificClient(){
        StationRenderAPI.getBakedModelManager().getModel(ModelIdentifier.of(Namespace.MINECRAFT.id("ko"), "ea"));
    }

    public static Minecraft getMc(){
        return Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());
    }
}
