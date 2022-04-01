package net.fabricmc.alkimicraft.init.tags;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTagsInit {
    public static final TagKey<Item> BURNABLE_ITEMS = TagKey.of(Registry.ITEM_KEY, new Identifier(AlkimiCraft.MOD_ID, "burnable_items"));
    public static final TagKey<Item> DESERT_POPLAR_LOGS = TagKey.of(Registry.ITEM_KEY, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_logs"));
        public static final TagKey<Item> PICKAXES = TagKey.of(Registry.ITEM_KEY, new Identifier(AlkimiCraft.MOD_ID, "pickaxes"));


    public static void init(){}
}
