package net.fabricmc.alkimicraft.utils;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ItemRenders {

    public static void defineRenders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0xFFD21B, ItemInit.DESERT_POPLAR_LEAVES);

    }
}
