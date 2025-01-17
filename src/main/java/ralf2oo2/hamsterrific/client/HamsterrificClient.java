package ralf2oo2.hamsterrific.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class HamsterrificClient {
    public static Minecraft getMc(){
        return Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());
    }
}
