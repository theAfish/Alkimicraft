package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.BlockHitResult;
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

public class PencilPlantTop extends Block {
    public static final int MAX_AGE = 5;
    public static final IntProperty AGE;
    private final PencilPlant plantBlock;
    private static final VoxelShape SHAPE;

    public PencilPlantTop(PencilPlant plantBlock, Settings settings) {
        super(settings);
        this.plantBlock = plantBlock;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 5;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos blockPos = pos.up();
        if (world.isAir(blockPos) && blockPos.getY() < world.getTopY()) {
            int i = state.get(AGE);
            if (i < MAX_AGE) {
                boolean bl = false;
                boolean bl2 = false;
                BlockState blockState = world.getBlockState(pos.down());
                int j;
                if (blockState.isOf(BlockInit.LOAMY_SAND)) {
                    bl = true;
                } else if (blockState.isOf(this.plantBlock)) {
                    j = 1;

                    for(int k = 0; k < 4; ++k) {
                        BlockState blockState2 = world.getBlockState(pos.down(j + 1));
                        if (!blockState2.isOf(this.plantBlock)) {
                            if (blockState2.isOf(BlockInit.LOAMY_SAND)) {
                                bl2 = true;
                            }
                            break;
                        }

                        ++j;
                    }

                    if (j < 2 || j <= random.nextInt(bl2 ? 3 : 2)) {
                        bl = true;
                    }
                } else if (blockState.isAir()) {
                    bl = true;
                }

                if (bl && isSurroundedByAir(world, blockPos, (Direction)null) && world.isAir(pos.up(2))) {
                    world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
                    this.grow(world, blockPos, i);
                } else if (i < 4) {
                    j = random.nextInt(4);
                    if (bl2) {
                        ++j;
                    }

                    boolean k = false;

                    for(int blockState2 = 0; blockState2 < j; ++blockState2) {
                        Direction direction = Direction.Type.HORIZONTAL.random(random);
                        BlockPos blockPos2 = pos.offset(direction);
                        if (world.isAir(blockPos2) && world.isAir(blockPos2.down()) && isSurroundedByAir(world, blockPos2, direction.getOpposite())) {
                            this.grow(world, blockPos2.up(), i + 2);
                            world.setBlockState(blockPos2, this.plantBlock.withConnectionProperties(world, blockPos2), 2);
                            k = true;
                        }
                    }

                    if (k) {
                        world.setBlockState(pos, this.plantBlock.withConnectionProperties(world, pos), 2);
                    } else {
                        this.die(world, pos);
                    }
                } else {
                    this.die(world, pos);
                }

            }
        }
    }

    private void grow(World world, BlockPos pos, int age) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, age), 2);
//        world.syncWorldEvent(1033, pos, 0);
    }

    private void die(World world, BlockPos pos) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, 5), 2);
//        world.syncWorldEvent(1034, pos, 0);
    }

    private static boolean isSurroundedByAir(WorldView world, BlockPos pos, @Nullable Direction exceptDirection) {
        Iterator var3 = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        do {
            if (!var3.hasNext()) {
                return true;
            }

            direction = (Direction)var3.next();
        } while(direction == exceptDirection || world.isAir(pos.offset(direction)));

        return false;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction != Direction.UP && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(this.plantBlock) || blockState.isOf(BlockInit.LOAMY_SAND);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    public static void generate(WorldAccess world, BlockPos pos, Random random, int size) {
        world.setBlockState(pos, ((PencilPlant)BlockInit.PENCIL_PLANT).withConnectionProperties(world, pos), 2);
        generate(world, pos, random, pos, size, 0);
    }

    private static void generate(WorldAccess world, BlockPos pos, Random random, BlockPos rootPos, int size, int layer) {
        PencilPlant pencilPlant = (PencilPlant)BlockInit.PENCIL_PLANT;
        int i = random.nextInt(2) + 1;
        if (layer == 0) {
            ++i;
        }

        for(int j = 0; j < i; ++j) {
            BlockPos blockPos = pos.up(j + 1);
            if (!isSurroundedByAir(world, blockPos, (Direction)null)) {
                return;
            }

            world.setBlockState(blockPos, pencilPlant.withConnectionProperties(world, blockPos), 2);
            world.setBlockState(blockPos.down(), pencilPlant.withConnectionProperties(world, blockPos.down()), 2);
        }

        boolean j = false;
        if (layer < 2) {
            int blockPos = random.nextInt(3);
            if (layer == 0) {
                ++blockPos;
            }

            for(int k = 0; k < blockPos; ++k) {
                Direction direction = Direction.Type.HORIZONTAL.random(random);
                BlockPos blockPos2 = pos.up(i).offset(direction);
                if (Math.abs(blockPos2.getX() - rootPos.getX()) < size && Math.abs(blockPos2.getZ() - rootPos.getZ()) < size && world.isAir(blockPos2) && world.isAir(blockPos2.down()) && isSurroundedByAir(world, blockPos2, direction.getOpposite())) {
                    j = true;
                    world.setBlockState(blockPos2, pencilPlant.withConnectionProperties(world, blockPos2), 2);
                    world.setBlockState(blockPos2.offset(direction.getOpposite()), pencilPlant.withConnectionProperties(world, blockPos2.offset(direction.getOpposite())), 2);
                    generate(world, blockPos2, random, rootPos, size, layer + 1);
                }
            }
        }

        if (!j) {
            world.setBlockState(pos.up(i), BlockInit.PENCIL_PLANT_TOP.getDefaultState().with(AGE, 5), 2);
        }

    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!world.isClient && projectile.canModifyAt(world, blockPos) && projectile.getType().isIn(EntityTypeTags.IMPACT_PROJECTILES)) {
            world.breakBlock(blockPos, true, projectile);
        }

    }

    static {
        AGE = Properties.AGE_5;
        SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    }
}
