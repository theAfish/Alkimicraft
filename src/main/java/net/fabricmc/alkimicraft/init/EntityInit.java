package net.fabricmc.alkimicraft.init;


import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.entity.CoppoiseEntity;
import net.fabricmc.alkimicraft.entity.StarryBodyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class EntityInit {

    public static final EntityType<StarryBodyEntity> STARRY_BODY = Registry.register(Registry.ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"starry_body"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, StarryBodyEntity::new).dimensions(EntityDimensions.fixed(0.125f,0.125f)).build());
    public static final EntityType<CoppoiseEntity> COPPOISE = Registry.register(Registry.ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"coppoise"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CoppoiseEntity::new).specificSpawnBlocks(Blocks.STONE, Blocks.DEEPSLATE, Blocks.SANDSTONE, Blocks.ANDESITE).dimensions(EntityDimensions.changing(0.5f,0.4f)).build());


    public static void init(){
        FabricDefaultAttributeRegistry.register(STARRY_BODY, StarryBodyEntity.createMobAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3));
        FabricDefaultAttributeRegistry.register(COPPOISE, CoppoiseEntity.createMobAttributes());
    }

}
