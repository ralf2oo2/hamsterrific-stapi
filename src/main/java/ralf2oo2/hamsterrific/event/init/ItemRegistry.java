package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.item.DebugItem;
import ralf2oo2.hamsterrific.item.HamsterBallItem;

public class ItemRegistry {

    public static Item hamsterBallItem;
    public static Item hamsterFoodItem;
    public static Item debugStick;
    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        debugStick = new DebugItem(Identifier.of(Hamsterrific.NAMESPACE, "debug")).setTranslationKey(Hamsterrific.NAMESPACE, "debug");
        hamsterBallItem = new HamsterBallItem(Identifier.of(Hamsterrific.NAMESPACE, "hamster_ball_item")).setTranslationKey("hamster_ball_item");
        hamsterFoodItem = new TemplateItem(Identifier.of(Hamsterrific.NAMESPACE, "hamster_food_item")).setTranslationKey("hamster_food_item");
    }
}
