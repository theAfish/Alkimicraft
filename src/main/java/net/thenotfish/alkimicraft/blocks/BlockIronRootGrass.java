package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.thenotfish.alkimicraft.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static net.thenotfish.alkimicraft.blocks.BlockProperties.fluidLogged;

public class BlockIronRootGrass extends CropBlock {

    public BlockIronRootGrass(Settings builder) {
        super(builder);
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.COBBLESTONE) || floor.isOf(BlockInit.POROUS_STONE) || floor.isOf(BlockInit.IRON_COBBLESTONE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge() - 1) {
                if (random.nextInt(5) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                    if (world.getBlockState(pos.down()).isOf(Blocks.COBBLESTONE) && world.random.nextInt(6) == 1){
                        world.setBlockState(pos.down(), BlockInit.POROUS_STONE.getDefaultState());
                    }
                }
            }else if (i < this.getMaxAge()){
                if (random.nextInt(10) == 0){
                    if (world.getBlockState(pos.down()).isOf(Blocks.COBBLESTONE)) {
                        world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                        world.setBlockState(pos.down(), BlockInit.IRON_COBBLESTONE.getDefaultState());
                    }else if (world.getBlockState(pos.down()).isOf(BlockInit.POROUS_STONE)){
                        if (world.getBlockState(pos.down()).get(fluidLogged).height%8 == 0) {
                            world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                            world.setBlockState(pos.down(), BlockInit.IRON_COBBLESTONE.getDefaultState());
                        } else {
                            ItemEntity iron_nugget_entity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() - 1.5D, (double)pos.getZ()+0.5D, new ItemStack(Items.IRON_NUGGET, 2));
                            world.spawnEntity(iron_nugget_entity);
                        }
                    }else if (world.getBlockState(pos.down()).isOf(BlockInit.IRON_COBBLESTONE)){
                        world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }

    }

    public ItemConvertible getSeedsItem() {
        return ItemInit.IRON_ROOT_GRASS_SEED;
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add((new TranslatableText("block.alkimicraft.iron_root_grass").formatted(Formatting.GREEN)));
    }

}
