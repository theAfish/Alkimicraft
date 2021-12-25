package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.blocks.entities.PackingTableBlockEntity;
import net.fabricmc.alkimicraft.blocks.entities.TipiFireEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PackingTableBlock extends Block implements BlockEntityProvider {
    protected static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0d,0d,0d,2d,12d,2d),
            Block.createCuboidShape(14d,0d,14d,16d,12d,16d),
            Block.createCuboidShape(14d,0d,0d,16d,12d,2d),
            Block.createCuboidShape(0d,0d,14d,2d,12d,16d));

    public PackingTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PackingTableBlockEntity packingTableBlockEntity) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!world.isClient && packingTableBlockEntity.addItem(player.getAbilities().creativeMode ? itemStack.copy() : itemStack)){
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PackingTableBlockEntity) {
                ItemScatterer.spawn(world, pos, ((PackingTableBlockEntity)blockEntity).getItemsWaitingPacked());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PackingTableBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


}
