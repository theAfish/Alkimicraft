package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BlockIronCobblestone extends Block implements Waterloggable, IFluidLoggable {

    public BlockIronCobblestone(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(ItemTags.CLUSTER_MAX_HARVESTABLES)){
            if (!world.isClient) {
                Direction direction = hit.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
                world.setBlockState(pos, BlockInit.POROUS_STONE.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D + (double)direction2.getOffsetX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction2.getOffsetZ() * 0.65D, new ItemStack(Items.IRON_NUGGET, 3));
                itemEntity.setVelocity(0.05D * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02D);
                itemStack.damage(1, (LivingEntity) player, p -> p.sendToolBreakStatus(hand));
                world.spawnEntity(itemEntity);
                BlockState bs = world.getBlockState(pos.up());
                if (bs.isOf(BlockInit.IRON_ROOT_GRASS)){
                    if (bs.get(Properties.AGE_7) == 7){
                        world.setBlockState(pos.up(), bs.with(Properties.AGE_7, 6), Block.NOTIFY_LISTENERS);
                    }
                }
            }
            return ActionResult.success(world.isClient);
        }else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}
