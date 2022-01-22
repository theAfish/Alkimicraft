package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.blocks.entities.WoodenBarrelEntity;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public abstract class AbstractWoodenBarrel extends BlockWithEntity implements BlockEntityProvider {
    protected static final VoxelShape OUTLINE_SHAPE;
    private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private final Map<Item, CauldronBehavior> behaviorMap;

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isEmpty()){
            CauldronBehavior cauldronBehavior = this.behaviorMap.get(itemStack.getItem());
            return cauldronBehavior.interact(state, world, pos, player, hand, itemStack);
        } else if (!world.isClient) {
            WoodenBarrelEntity blockEntity = (WoodenBarrelEntity) world.getBlockEntity(pos);
            // 这里会调用 BlockWithEntity 的 createScreenHandlerFactory 方法, 他会转换为
            // 一个 namedScreenHandlerFactory。 如果你的方块没有继承 BlockWithEntity，那就需要单独实现 createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
                player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                return ActionResult.CONSUME;
            }
            return ActionResult.FAIL;
        }
        return ActionResult.FAIL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WoodenBarrelEntity) {
                ItemScatterer.spawn(world, pos, (WoodenBarrelEntity)blockEntity);
                // 更新比较器
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    protected double getFluidHeight(BlockState state) {
        return 0.0D;
    }

    protected boolean isEntityTouchingFluid(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double)pos.getY() + this.getFluidHeight(state) && entity.getBoundingBox().maxY > (double)pos.getY() + 0.25D;
    }

    public AbstractWoodenBarrel(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings);
        this.behaviorMap = behaviorMap;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public abstract boolean isFull(BlockState state);

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos = PointedDripstoneBlock.getDripPos(world, pos);
        if (blockPos != null) {
            Fluid fluid = PointedDripstoneBlock.getDripFluid(world, blockPos);
            if (fluid != Fluids.EMPTY && this.canBeFilledByDripstone(fluid)) {
                this.fillFromDripstone(state, world, pos, fluid);
            }
        }
    }

    protected boolean canBeFilledByDripstone(Fluid fluid) {
        return false;
    }

    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
    }
    static {
        OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), RAYCAST_SHAPE, BooleanBiFunction.ONLY_FIRST);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenBarrelEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
