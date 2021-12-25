package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.FluidInit;
import net.fabricmc.alkimicraft.utils.Defaults;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import static net.fabricmc.alkimicraft.blocks.BlockProperties.fluidLogged;

public class BlockPorousStone extends Block implements Waterloggable, IFluidLoggable {

    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<LoggableFluidsEnum> FLUIDLOGGED;

    public BlockPorousStone(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(FLUIDLOGGED, LoggableFluidsEnum.EMPTY)));
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext) {
            EntityShapeContext entityShapeContext = (EntityShapeContext)context;
            Optional<Entity> optional = Optional.ofNullable(entityShapeContext.getEntity());
            if (optional.isPresent()) {
                Entity entity = (Entity)optional.get();

                if (!isPowder(entity)) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }
        return VoxelShapes.empty();
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.8999999761581421D, 0.9D, 0.9D));
        }
    }

    public static boolean isPowder(Entity entity){
        if (entity instanceof ItemEntity) {
            Item item = ((ItemEntity)entity).getStack().getItem();
            return item == Items.SUGAR || item == Items.REDSTONE || item == Items.IRON_NUGGET || item == Items.GOLD_NUGGET;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return Defaults.getFluidState(state);
    }


    @Override
    public boolean tryFillWithFluid(WorldAccess worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn)
    {
        return Defaults.tryFillWithFluid(this, worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canFillWithFluid(BlockView worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
    {
        return Defaults.canFillWithFluid(this, worldIn, pos, state, fluidIn);
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess worldIn, BlockPos pos, BlockState state)
    {
        return Defaults.tryDrainFluid(this, worldIn, pos, state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FLUIDLOGGED, LoggableFluidsEnum.getByFluid(fluidState.getFluid(), fluidState.getLevel()));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction direction, BlockState neighborState, WorldAccess worldIn, BlockPos currentPos, BlockPos neighborPos) {
        if (LoggableFluidsEnum.canFillWithFluid(stateIn, worldIn.getFluidState(currentPos).getFluid())) {
            stateIn.get(fluidLogged).contains.ifPresent(FlowableFluid -> worldIn.createAndScheduleFluidTick(currentPos, FlowableFluid, FlowableFluid.getTickRate(worldIn)));
        }
        FluidState fluidState = worldIn.getFluidState(currentPos);
//        if (!fluidState.isEmpty() && fluidState.getLevel() != 0) {
//            return this.getDefaultState().with(FLUIDLOGGED, LoggableFluidsEnum.getByFluid(fluidState.getFluid(), fluidState.getLevel()));
//        }
        return this.getDefaultState().with(FLUIDLOGGED, LoggableFluidsEnum.getByFluid(fluidState.getFluid(), fluidState.getLevel()));

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        FLUIDLOGGED = BlockProperties.fluidLogged;
    }
}
