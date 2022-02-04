package net.fabricmc.alkimicraft.blocks;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.Random;

public class BuddingOreBlock extends Block {
    public static final int GROW_CHANCE = 5;
    private final Block small_ore_bud;
    private final Block medium_ore_bud;
    private final Block large_ore_bud;
    private final Block ore_cluster;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingOreBlock(Settings settings, Block small_ore_bud, Block medium_ore_bud, Block large_ore_bud, Block ore_cluster) {
        super(settings);
        this.small_ore_bud = small_ore_bud;
        this.medium_ore_bud = medium_ore_bud;
        this.large_ore_bud = large_ore_bud;
        this.ore_cluster = ore_cluster;
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(GROW_CHANCE) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState) && isLavaNearby(world, pos)) {
                block = this.small_ore_bud;
            } else if (blockState.isOf(this.small_ore_bud) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = this.medium_ore_bud;
            } else if (blockState.isOf(this.medium_ore_bud) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = this.large_ore_bud;
            } else if (blockState.isOf(this.large_ore_bud) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = this.ore_cluster;
            }

            if (block != null) {
                BlockState blockState2 = block.getDefaultState().with(AmethystClusterBlock.FACING, direction).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(blockPos, blockState2);
            }

        }
    }

    private static boolean isLavaNearby(WorldView world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 1, 1)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!world.getFluidState(blockPos).isIn(FluidTags.LAVA));

        return true;
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}
