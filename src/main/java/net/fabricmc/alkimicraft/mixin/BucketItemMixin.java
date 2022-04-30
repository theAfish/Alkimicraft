package net.fabricmc.alkimicraft.mixin;

import net.fabricmc.alkimicraft.blocks.IFluidLoggable;
import net.fabricmc.alkimicraft.blocks.LoggableFluidsEnum;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

    @Final
    @Shadow
    private Fluid fluid;

    public BucketItemMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author theAfish
     * @reason To change behavior
     */
    @Overwrite
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.fluid instanceof FlowableFluid)) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBucketPlace(this.fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
            if (!bl2) {
                return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), (BlockHitResult)null);
            } else if (world.getDimension().isUltrawarm() && this.fluid.isIn(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                return true;
            } else if (block instanceof FluidFillable) {
                ((FluidFillable)block).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
                this.playEmptyingSound(player, world, pos);
                return true;
            } else {
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }

                if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !blockState.getFluidState().isStill()) {
                    return false;
                } else {
                    this.playEmptyingSound(player, world, pos);
                    return true;
                }
            }
        }
    }

    @Inject(method = "use", at=@At(value = "INVOKE",
            target = "Lnet/minecraft/item/BucketItem;placeFluid(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/hit/BlockHitResult;)Z"),
    cancellable = true)
    private void use_inject(World world, PlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        BlockHitResult blockHitResult = raycast(world, playerEntity, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos3 = blockPos.offset(blockHitResult.getSide());
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        if (blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER){
            blockPos3 = blockPos;
        }
        if (blockState.getBlock() instanceof IFluidLoggable){
            if (LoggableFluidsEnum.getByFluid(this.fluid, 8).canFillWith(blockState)){
                blockPos3 = blockPos;
            }
        }
        if (this.placeFluid(playerEntity, world, blockPos3, blockHitResult)) {
            this.onEmptied(playerEntity, world, itemStack, blockPos3);
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos3, itemStack);
            }

            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            cir.setReturnValue(TypedActionResult.success(getEmptiedStack(itemStack, playerEntity), world.isClient()));
        } else {
            cir.setReturnValue(TypedActionResult.fail(itemStack));
        }
    }

//    /**
//     * @author theAfish
//     * @reason To change the behavior
//     */
//    @Overwrite
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack itemStack = user.getStackInHand(hand);
//        BlockHitResult blockHitResult = raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
//        if (blockHitResult.getType() == HitResult.Type.MISS) {
//            return TypedActionResult.pass(itemStack);
//        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
//            return TypedActionResult.pass(itemStack);
//        } else {
//            BlockPos blockPos = blockHitResult.getBlockPos();
//            Direction direction = blockHitResult.getSide();
//            BlockPos blockPos2 = blockPos.offset(direction);
//            if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
//                BlockState blockState;
//                if (this.fluid == Fluids.EMPTY) {
//                    blockState = world.getBlockState(blockPos);
//                    if (blockState.getBlock() instanceof FluidDrainable) {
//                        FluidDrainable fluidDrainable = (FluidDrainable)blockState.getBlock();
//                        ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
//                        if (!itemStack2.isEmpty()) {
//                            user.incrementStat(Stats.USED.getOrCreateStat(this));
//                            fluidDrainable.getBucketFillSound().ifPresent((sound) -> {
//                                user.playSound(sound, 1.0F, 1.0F);
//                            });
//                            world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
//                            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
//                            if (!world.isClient) {
//                                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
//                            }
//
//                            return TypedActionResult.success(itemStack3, world.isClient());
//                        }
//                    }
//
//                    return TypedActionResult.fail(itemStack);
//                } else {
//                    blockState = world.getBlockState(blockPos);
//                    BlockPos fluidDrainable = blockPos2;
//                    if (blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER){
//                        fluidDrainable = blockPos;
//                    }
//                    if (blockState.getBlock() instanceof IFluidLoggable){
//                        if (LoggableFluidsEnum.getByFluid(this.fluid, 8).canFillWith(blockState)){
//                            fluidDrainable = blockPos;
//                        }
//                    }
//                    if (this.placeFluid(user, world, fluidDrainable, blockHitResult)) {
//                        this.onEmptied(user, world, itemStack, fluidDrainable);
//                        if (user instanceof ServerPlayerEntity) {
//                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, fluidDrainable, itemStack);
//                        }
//
//                        user.incrementStat(Stats.USED.getOrCreateStat(this));
//                        return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
//                    } else {
//                        return TypedActionResult.fail(itemStack);
//                    }
//                }
//            } else {
//                return TypedActionResult.fail(itemStack);
//            }
//        }
//    }

    @Shadow
    public abstract void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos);

    @Shadow
    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return null;
    }

    @Shadow
    protected abstract void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos);
}
