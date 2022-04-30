package net.thenotfish.alkimicraft.init.tags;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockTagsInit {
    public static final TagKey<Block> DESERT_POPLAR_LOGS = TagKey.of(Registry.BLOCK_KEY, new Identifier(AlkimiCraft.MOD_ID, "desert_poplar_logs"));
    public static final TagKey<Block> WOODEN_BARREL = TagKey.of(Registry.BLOCK_KEY,new Identifier(AlkimiCraft.MOD_ID, "wooden_barrel"));


    public static void init(){}
}
