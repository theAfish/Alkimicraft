package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.blocks.entities.PackingTableBlockEntity;
import net.fabricmc.alkimicraft.blocks.entities.TipiFireEntity;
import net.fabricmc.alkimicraft.blocks.entities.WoodenBarrelEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<TipiFireEntity> TIPI_FIRE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"tipi_fire"), FabricBlockEntityTypeBuilder.create(TipiFireEntity::new, BlockInit.TIPI_FIRE).build());
    public static BlockEntityType<PackingTableBlockEntity> PACKING_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), FabricBlockEntityTypeBuilder.create(PackingTableBlockEntity::new, BlockInit.PACKING_TABLE).build());
    public static BlockEntityType<WoodenBarrelEntity> WOODEN_BARREL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID, "wooden_barrel"), FabricBlockEntityTypeBuilder.create(WoodenBarrelEntity::new, BlockInit.WOODEN_BARREL_WATER).build());


    public static void init(){

    }
}
