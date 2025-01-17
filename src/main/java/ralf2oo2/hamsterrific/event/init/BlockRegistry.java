package ralf2oo2.hamsterrific.event.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.hamsterrific.Hamsterrific;
import ralf2oo2.hamsterrific.block.WaterBottleBlock;

public class BlockRegistry {
    public static Block waterBottleBlock;

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        waterBottleBlock = new WaterBottleBlock(Identifier.of(Hamsterrific.NAMESPACE, "water_bottle"), Material.STONE).setTranslationKey(Hamsterrific.NAMESPACE, "water_bottle");
    }
}
