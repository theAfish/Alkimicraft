package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.blocks.entities.MicroscopeEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class Microscope extends BlockWithEntity implements BlockEntityProvider{
    public static final VoxelShape shape = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);

    public static final DirectionProperty FACING;

    public Microscope(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            MicroscopeEntity blockEntity = (MicroscopeEntity) world.getBlockEntity(pos);
            // 这里会调用 BlockWithEntity 的 createScreenHandlerFactory 方法, 他会转换为
            // 一个 namedScreenHandlerFactory。 如果你的方块没有继承 BlockWithEntity，那就需要单独实现 createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
//                player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                return ActionResult.CONSUME;
            }
            return ActionResult.FAIL;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(FACING));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView worldView, BlockPos blockPos) {
        return Block.sideCoversSmallSquare(worldView, blockPos.offset(Direction.DOWN), Direction.UP);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState2, WorldAccess worldAccess, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !blockState.canPlaceAt(worldAccess, blockPos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(blockState, direction, blockState2, worldAccess, blockPos, blockPos2);
    }

    static {
        FACING = Properties.FACING;

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MicroscopeEntity(blockPos, blockState);
    }
}
