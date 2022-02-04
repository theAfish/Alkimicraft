package net.fabricmc.alkimicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;

public abstract class AbstractThinTreeTop extends Block {
    public static final int MAX_AGE = 5;
    public static final IntProperty AGE;
    private final ThinTreeLog logBlock;
    private final Block leaveBlock;
    private final Block soilBlock;
    private static final VoxelShape SHAPE;

    public AbstractThinTreeTop(ThinTreeLog plantBlock, Block leaveBlock, Block soilBlock, Settings settings) {
        super(settings);
        this.logBlock = plantBlock;
        this.leaveBlock = leaveBlock;
        this.soilBlock = soilBlock;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

    }

//    @Override
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return SHAPE;
//    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 5;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos = pos.up();
        if ((world.isAir(blockPos)||world.getBlockState(blockPos).isOf(this.leaveBlock)) && blockPos.getY() < world.getTopY()) {
            int i = state.get(AGE);
            if (i < MAX_AGE) {
                boolean bl = false;
                boolean bl2 = false;
                BlockState blockState = world.getBlockState(pos.down());
                int j;
                if (blockState.isOf(this.soilBlock)) {
                    bl = true;
                } else if (blockState.isOf(this.logBlock)) {
                    j = 1;

                    for(int k = 0; k < 4; ++k) {
                        BlockState blockState2 = world.getBlockState(pos.down(j + 1));
                        if (!blockState2.isOf(this.logBlock)) {
                            if (blockState2.isOf(this.soilBlock)) {
                                bl2 = true;
                            }
                            break;
                        }
                        ++j;
                    }

                    if (j < 2 || j <= random.nextInt(bl2 ? 3 : 2)) {
                        bl = true;
                    }
                } else if (blockState.isAir() || blockState.isOf(this.leaveBlock)) {
                    bl = true;
                }

                if (bl && isSurroundedByAirOrLeave(world, blockPos, (Direction)null) && world.isAir(pos.up(2))) {
                    world.setBlockState(pos, this.logBlock.withConnectionProperties(world, pos), 2);
                    this.grow(world, blockPos, i);
                    if (i >= 2) this.growLeaves(world, blockPos.down());
                } else if (i < 4) {
                    j = random.nextInt(4);
                    if (bl2) {
                        ++j;
                    }

                    boolean k = false;

                    for(int blockState2 = 0; blockState2 < j; ++blockState2) {
                        Direction direction = Direction.Type.HORIZONTAL.random(random);
                        BlockPos blockPos2 = pos.offset(direction);
                        if ((world.isAir(blockPos2) ||world.getBlockState(blockPos2).isOf(this.leaveBlock)) && (world.isAir(blockPos2.down())||world.getBlockState(blockPos2.down()).isOf(this.leaveBlock)) && isSurroundedByAirOrLeave(world, blockPos2, direction.getOpposite())) {
                            this.grow(world, blockPos2.up(), i + 2);
                            this.growLeaves(world, blockPos2);
                            world.setBlockState(blockPos2, this.logBlock.withConnectionProperties(world, blockPos2), 2);
                            k = true;
                        }
                    }

                    if (k) {
                        world.setBlockState(pos, this.logBlock.withConnectionProperties(world, pos), 2);
                    } else {
                        this.die(world, pos);
                    }
                } else {
                    this.die(world, pos);
                }

            }
        }
    }

    protected void grow(World world, BlockPos pos, int age) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, age), 2);
//        world.syncWorldEvent(1033, pos, 0);
    }

    protected void die(World world, BlockPos pos) {
        world.setBlockState(pos, leaveBlock.getDefaultState(), 2);
//        world.syncWorldEvent(1034, pos, 0);
    }

    private boolean isSurroundedByAirOrLeave(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
        Iterator<Direction> var3 = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        do {
            if (!var3.hasNext()) {
                return true;
            }

            direction = var3.next();
        } while(direction == exceptDirection || world.isAir(pos.offset(direction)) || world.getBlockState(pos.offset(direction)).isOf(this.leaveBlock));

        return false;
    }

    private void growLeaves(World world, BlockPos pos){
        Iterator<Direction> nswe = Direction.Type.HORIZONTAL.iterator();
        Direction direction;
        while (nswe.hasNext()){
            direction = nswe.next();
            if (world.isAir(pos.offset(direction))){
                world.setBlockState(pos.offset(direction), this.leaveBlock.getDefaultState().with(Properties.DISTANCE_1_7, 1));
            }
        }

    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction != Direction.UP && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(this.logBlock) || blockState.isOf(this.soilBlock);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public void generate(WorldAccess world, BlockPos pos, Random random, int size) {
        world.setBlockState(pos, (this.logBlock).withConnectionProperties(world, pos), 2);
        generate(world, pos, random, pos, size, 0);
    }

    private void generate(WorldAccess world, BlockPos pos, Random random, BlockPos rootPos, int size, int layer) {
        int i = random.nextInt(4) + 1;
        if (layer == 0) {
            ++i;
        }

        for(int j = 0; j < i; ++j) {
            BlockPos blockPos = pos.up(j + 1);
            if (!isSurroundedByAirOrLeave(world, blockPos, (Direction)null)) {
                return;
            }

            world.setBlockState(blockPos, this.logBlock.withConnectionProperties(world, blockPos), 2);
            world.setBlockState(blockPos.down(), this.logBlock.withConnectionProperties(world, blockPos.down()), 2);
        }

        boolean j = false;
        if (layer < 4) {
            int blockPos = random.nextInt(4);
            if (layer == 0) {
                ++blockPos;
            }

            for(int k = 0; k < blockPos; ++k) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                BlockPos blockPos2 = pos.up(i).offset(direction);
                if (Math.abs(blockPos2.getX() - rootPos.getX()) < size && Math.abs(blockPos2.getZ() - rootPos.getZ()) < size && world.isAir(blockPos2) && world.isAir(blockPos2.down()) && isSurroundedByAirOrLeave(world, blockPos2, direction.getOpposite())) {
                    j = true;
                    world.setBlockState(blockPos2, this.logBlock.withConnectionProperties(world, blockPos2), 2);
                    world.setBlockState(blockPos2.offset(direction.getOpposite()),this.logBlock.withConnectionProperties(world, blockPos2.offset(direction.getOpposite())), 2);
                    generate(world, blockPos2, random, rootPos, size, layer + 1);
                }
            }
        }

        if (!j) {
            world.setBlockState(pos.up(i), this.getDefaultState().with(AGE, 5), 2);
        }

    }

    static {
        AGE = Properties.AGE_5;
        SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    }
}
