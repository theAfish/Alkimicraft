package net.fabricmc.alkimicraft.init.tags;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BlockTagsInit {
    public static final Tag<Block> DESERT_POPLAR_LOGS = TagFactory.BLOCK.create(new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_logs"));

    public static void init(){}
}
