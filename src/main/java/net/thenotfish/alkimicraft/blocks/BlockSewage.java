package net.thenotfish.alkimicraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockSewage extends FluidBlock {
    protected BlockSewage(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Deprecated
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos position, Entity entity) {
        if (entity instanceof LivingEntity livingEntity){
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 0, false, false));
        }
//        entity.addVelocity(0.0d, 0.5d,0.0d);
        super.onEntityCollision(state, world, position, entity);
    }
}
