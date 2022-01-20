package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class FertileBeans extends CropBlock {

    public FertileBeans(Settings builder) {
        super(builder);
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(BlockInit.SANDY_LOAM) || floor.isOf(BlockInit.BARREN_LOAM);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge() - 1) {
                if (random.nextInt(3) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                }
            }else if (i < this.getMaxAge()){
                if (world.getBlockState(pos.down()).isOf(BlockInit.SANDY_LOAM)) {
                    if (random.nextInt(4) == 0){
                        world.setBlockState(pos.down(), BlockInit.BARREN_LOAM.getDefaultState());
                    }
                    world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                }else if (world.getBlockState(pos.down()).isOf(BlockInit.BARREN_LOAM)){
                    world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                }
            }
        }

    }

    public ItemConvertible getSeedsItem() {
        return ItemInit.FERTILE_BEANS_SEEDS;
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add((new TranslatableText("block.alkimicraft.fertile_beans").formatted(Formatting.GREEN)));
    }

}
