package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.AlkimiCraftClient;
import net.fabricmc.alkimicraft.blocks.entities.TipiFireEntity;
import net.fabricmc.alkimicraft.init.BlockEntityInit;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.EntityInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.fabricmc.alkimicraft.init.tags.ItemTagsInit;
import net.fabricmc.alkimicraft.init.tags.TagsInit;
import net.fabricmc.alkimicraft.utils.BlockEntityRenders;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class TipiFire extends BlockWithEntity implements Waterloggable, IFluidLoggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 5.0D, 13.0D);
    public static final BooleanProperty LIT;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<LoggableFluidsEnum> FLUIDLOGGED;
    public static final DirectionProperty FACING;

    private static final VoxelShape SMOKEY_SHAPE;
    private final boolean emitsParticles;
    private final int fireDamage;

    public TipiFire( boolean emitsParticles, int fireDamage, AbstractBlock.Settings settings) {
        super(settings);
        this.emitsParticles = emitsParticles;
        this.fireDamage = fireDamage;
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false).with(WATERLOGGED, false).with(FLUIDLOGGED, LoggableFluidsEnum.EMPTY).with(FACING, Direction.NORTH));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TipiFireEntity tipiFireEntity) {
            ItemStack itemStack = player.getStackInHand(hand);
            Optional<CampfireCookingRecipe> optional = tipiFireEntity.getRecipeFor(itemStack);
            if (optional.isPresent()) {
                if (!world.isClient && tipiFireEntity.addItem(player.getAbilities().creativeMode ? itemStack.copy() : itemStack, ((CampfireCookingRecipe)optional.get()).getCookTime())) {
                    player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResult.SUCCESS;
                }
                return ActionResult.CONSUME;
//            }else if (itemStack.isOf(Items.STICK)||itemStack.isIn(ItemTags.COALS) || itemStack.isIn(ItemTags.LOGS_THAT_BURN)||itemStack.isIn(ItemTags.PLANKS)||itemStack.isIn(ItemTags.LEAVES)){
            }else if (itemStack.isIn(ItemTagsInit.BURNABLE_ITEMS)){
                if(!world.isClient && tipiFireEntity.addBurningItem(player.getAbilities().creativeMode ? itemStack.copy() : itemStack, 300)){
                    player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
                    return ActionResult.SUCCESS;
                }
            }else{
                tipiFireEntity.getAsh(world,pos);
            }
        }

        return ActionResult.PASS;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune() && state.get(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.IN_FIRE, (float)this.fireDamage);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if (random.nextInt(10) == 0) {
                world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (this.emitsParticles && random.nextInt(5) == 0) {
                for(int i = 0; i < random.nextInt(3) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, random.nextFloat() / 2.5F, 4.5E-5D, random.nextFloat() / 2.5F);
                }
            }
            if (this.emitsParticles && random.nextInt(1) == 0) {
                for(int i = 0; i < random.nextInt(5) + 1; ++i) {
                    world.addParticle(ParticleTypes.FLAME, (double)pos.getX() + random.nextFloat() * 0.6 + 0.2, (double)pos.getY() + Math.max((random.nextFloat()-0.5), 0.0f), (double)pos.getZ() + random.nextFloat() * 0.6 + 0.2, 0d, 0.01D, 0d);
                }
            }

        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        boolean bl = worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(LIT, false).with(FACING, ctx.getPlayerFacing());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }else if (state.get(FLUIDLOGGED).contains.isPresent()){
            Fluid fluid = state.get(FLUIDLOGGED).contains.get();
            world.createAndScheduleFluidTick(pos, fluid, fluid.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static void extinguish(@Nullable Entity entity, WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            for(int i = 0; i < 10; ++i) {
                spawnSmokeParticle((World)world, pos, true);
            }
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CampfireBlockEntity) {
            ((CampfireBlockEntity)blockEntity).spawnItemsBeingCooked();
        }

        world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
    }

    public static void spawnSmokeParticle(World world, BlockPos pos, boolean lotsOfSmoke) {
        Random random = world.getRandom();
        DefaultParticleType defaultParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addImportantParticle(defaultParticleType, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.03D, 0.0D);
        if (lotsOfSmoke) {
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5D + random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.5D + random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TipiFireEntity(pos, state);
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TipiFireEntity) {
                ItemScatterer.spawn(world, pos, ((TipiFireEntity)blockEntity).getItemsBeingCooked());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(LIT) ? checkType(type, BlockEntityInit.TIPI_FIRE_ENTITY, TipiFireEntity::clientTick) : null;
        } else {
            return state.get(LIT) ? checkType(type, BlockEntityInit.TIPI_FIRE_ENTITY, TipiFireEntity::litServerTick) : checkType(type, BlockEntityInit.TIPI_FIRE_ENTITY, TipiFireEntity::unlitServerTick);
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, FACING);
    }

    static {
        LIT = Properties.LIT;
        WATERLOGGED = Properties.WATERLOGGED;
        FLUIDLOGGED = BlockProperties.fluidLogged;
        FACING = Properties.HORIZONTAL_FACING;
        SMOKEY_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    }
}
