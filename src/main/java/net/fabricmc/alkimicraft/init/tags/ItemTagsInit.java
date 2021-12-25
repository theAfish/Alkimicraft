package net.fabricmc.alkimicraft.init.tags;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.RequiredTagList;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTagsInit {
    public static final Tag<Item> BURNABLE_ITEMS = TagFactory.ITEM.create(new Identifier(AlkimiCraft.MOD_ID, "burnable_items"));
    public static final Tag<Item> DESERT_POPLAR_LOGS = TagFactory.ITEM.create(new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_logs"));

    public static void init(){}
}
