package net.fabricmc.alkimicraft.init;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.blocks.entities.TipiFireEntity;
import net.fabricmc.alkimicraft.entity.StarryBodyEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public class EntityInit {

    public static final EntityType<StarryBodyEntity> STARRY_BODY = Registry.register(Registry.ENTITY_TYPE, new Identifier(AlkimiCraft.MOD_ID,"starry_body"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, StarryBodyEntity::new).dimensions(EntityDimensions.fixed(0.125f,0.125f)).build());



    public static void init(){
        FabricDefaultAttributeRegistry.register(STARRY_BODY, StarryBodyEntity.createMobAttributes().add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3));
    }

}
