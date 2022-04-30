package net.thenotfish.alkimicraft.items;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class GrassSeeds extends Item {
    public GrassSeeds(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos hitPos = context.getBlockPos();
        BlockState hitBlockState = context.getWorld().getBlockState(hitPos);
        BlockState resultState = Blocks.AIR.getDefaultState();
        if (hitBlockState.isOf(Blocks.DIRT)) resultState = Blocks.GRASS_BLOCK.getDefaultState();
        else if (hitBlockState.isOf(BlockInit.BARREN_LOAM)) resultState = BlockInit.SPARSE_GRASS_BLOCK.getDefaultState();

        if (!resultState.isAir()) {
            context.getWorld().setBlockState(hitPos, resultState, 0);
            playUseSound(context.getWorld(), hitPos);
            context.getStack().decrement(1);
            return ActionResult.success(context.getWorld().isClient);
        }
        return super.useOnBlock(context);
    }

    private void playUseSound(World world, BlockPos pos) {
        Random random = world.getRandom();
        world.playSound(null, pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("grass_seeds.tooltip"));
    }
}
