package net.thenotfish.alkimicraft.init;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.blocks.entities.MicroscopeEntity;
import net.thenotfish.alkimicraft.blocks.entities.PackingTableBlockEntity;
import net.thenotfish.alkimicraft.blocks.entities.TipiFireEntity;
import net.thenotfish.alkimicraft.blocks.entities.WoodenBarrelEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityInit {
    public static BlockEntityType<PackingTableBlockEntity> PACKING_TABLE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID, "packing_table"), FabricBlockEntityTypeBuilder.create(PackingTableBlockEntity::new, BlockInit.PACKING_TABLE).build(null));
    public static BlockEntityType<WoodenBarrelEntity> WOODEN_BARREL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID, "wooden_barrel"), FabricBlockEntityTypeBuilder.create(WoodenBarrelEntity::new, BlockInit.WOODEN_BARREL_WATER).build(null));
    public static BlockEntityType<TipiFireEntity> TIPI_FIRE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"tipi_fire"), FabricBlockEntityTypeBuilder.create(TipiFireEntity::new, BlockInit.TIPI_FIRE).build(null));
    public static BlockEntityType<MicroscopeEntity> MICROSCOPE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"microscope"), FabricBlockEntityTypeBuilder.create(MicroscopeEntity::new, BlockInit.MICROSCOPE).build(null));


    public static void init(){

    }
}
