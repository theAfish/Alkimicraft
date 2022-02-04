package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CopperElsholtzia extends CropBlock {
    private static final VoxelShape[] AGE_TO_SHAPE;

    public CopperElsholtzia(Settings builder) {
        super(builder);
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.GRAVEL) || floor.isOf(BlockInit.BARREN_LOAM) || floor.isOf(BlockInit.SPARSE_GRASS_BLOCK) || floor.isIn(BlockTags.DIRT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 3) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()){
                world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
            }
        }

    }

    public ItemConvertible getSeedsItem() {
        return ItemInit.COPPER_ELSHOLTZIA_SEEDS;
    }

    static {
        AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
                Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 3.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D),
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D)};

    }
}
