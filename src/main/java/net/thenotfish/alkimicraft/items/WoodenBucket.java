package net.thenotfish.alkimicraft.items;

import net.thenotfish.alkimicraft.blocks.BlockProperties;
import net.thenotfish.alkimicraft.blocks.IFluidLoggable;
import net.thenotfish.alkimicraft.blocks.LoggableFluidsEnum;
import net.thenotfish.alkimicraft.init.ItemInit;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WoodenBucket extends BucketItem {
    public final Fluid fluid;

    public WoodenBucket(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
                BlockState blockState;
                if (this.fluid == Fluids.EMPTY) {
                    blockState = world.getBlockState(blockPos);
                    if (blockState.getBlock() instanceof FluidDrainable fluidDrainable) {
                        if (blockState.getBlock() instanceof IFluidLoggable){
                            if (blockState.get(BlockProperties.fluidLogged).contains.orElse(Fluids.WATER).matchesType(Fluids.LAVA)){
                                return TypedActionResult.pass(itemStack);
                            }
                        }else if (blockState.isOf(Blocks.LAVA)){
                            return TypedActionResult.pass(itemStack);
                        }
                        ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
                        if (!itemStack2.isEmpty() && !itemStack2.isOf(Items.LAVA_BUCKET)) {
                            user.incrementStat(Stats.USED.getOrCreateStat(this));
                            fluidDrainable.getBucketFillSound().ifPresent((sound) -> {
                                user.playSound(sound, 1.0F, 1.0F);
                            });
                            world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                            itemStack2 = itemStack2.isOf(Items.WATER_BUCKET) ? ItemInit.WATER_WOODEN_BUCKET.getDefaultStack() : ItemInit.SEWAGE_WOODEN_BUCKET.getDefaultStack();
                            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                            if (!world.isClient) {
                                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
                            }

                            return TypedActionResult.success(itemStack3, world.isClient());
                        }
                    }

                    return TypedActionResult.fail(itemStack);
                } else {
                    blockState = world.getBlockState(blockPos);
                    BlockPos fluidDrainable = blockPos2;
                    if (blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER){
                        fluidDrainable = blockPos;
                    }
                    if (blockState.getBlock() instanceof IFluidLoggable){
                        if (LoggableFluidsEnum.getByFluid(this.fluid, 8).canFillWith(blockState)){
                            fluidDrainable = blockPos;
                        }
                    }
                    if (this.placeFluid(user, world, fluidDrainable, blockHitResult)) {
                        this.onEmptied(user, world, itemStack, fluidDrainable);
                        if (user instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, fluidDrainable, itemStack);
                        }

                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
                    } else {
                        return TypedActionResult.fail(itemStack);
                    }
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }

    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return !player.getAbilities().creativeMode ? new ItemStack(ItemInit.WOODEN_BUCKET) : stack;
    }
}
