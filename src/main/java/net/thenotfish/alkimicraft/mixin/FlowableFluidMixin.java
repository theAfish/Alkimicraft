package net.thenotfish.alkimicraft.mixin;

import net.thenotfish.alkimicraft.blocks.IFluidLoggable;
import net.thenotfish.alkimicraft.blocks.LoggableFluidsEnum;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.thenotfish.alkimicraft.blocks.BlockProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin extends Fluid {

    /**
     * @author theAfish
     * @reason Original fluid is bad
     */
    @Overwrite
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        if (!state.isStill()) {
            FluidState fluidState = this.getUpdatedState(world, pos, world.getBlockState(pos));
            BlockState blockState = world.getBlockState(pos);
            int i = this.getNextTickDelay(world, pos, state, fluidState);
            if (fluidState.isEmpty()) {
                state = fluidState;
                if (blockState.getBlock() instanceof IFluidLoggable){
                    world.setBlockState(pos, blockState.with(BlockProperties.fluidLogged, LoggableFluidsEnum.EMPTY), 3);
                }
                else{
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                }
            } else if (!fluidState.equals(state)) {
                if (blockState.getBlock() instanceof IFluidLoggable){
                    LoggableFluidsEnum fluid = LoggableFluidsEnum.getByFluid(fluidState.getFluid(), fluidState.getLevel());
                    world.setBlockState(pos, blockState.with(BlockProperties.fluidLogged, fluid), 3);
                }
                else{
                    BlockState bs = fluidState.getBlockState();
                    world.setBlockState(pos, bs, 2);
                    world.createAndScheduleFluidTick(pos, fluidState.getFluid(), i);
                    world.updateNeighborsAlways(pos, bs.getBlock());
                }
            }
        }

        this.tryFlow(world, pos, state);
    }

    /**
     * @author theAfish
     * @reason Set water finite in the_dry
     */
    @Overwrite
    public FluidState getUpdatedState(WorldView world, BlockPos pos, BlockState state) {
        int i = 0;
        int j = 0;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            FluidState fluidState = blockState.getFluidState();
            if (fluidState.getFluid().matchesType(this) && this.receivesFlow(direction, world, pos, state, blockPos, blockState)) {
                if (fluidState.isStill()) {
                    ++j;
                }

                i = Math.max(i, fluidState.getLevel());
            }
        }
        if ((this.isInfinite() && !world.getDimension().isRespawnAnchorWorking()) && j >= 2) {
            BlockState blockState2 = world.getBlockState(pos.down());
            FluidState direction = blockState2.getFluidState();
            if (blockState2.getMaterial().isSolid() || this.isMatchingAndStill(direction)) {
                return this.getStill(false);
            }
        }

        BlockPos blockState2 = pos.up();
        BlockState direction = world.getBlockState(blockState2);
        FluidState blockPos = direction.getFluidState();
        if (!blockPos.isEmpty() && blockPos.getFluid().matchesType(this) && this.receivesFlow(Direction.UP, world, pos, state, blockState2, direction)) {
            return this.getFlowing(8, true);
        } else {
            int blockState = i - this.getLevelDecreasePerBlock(world);
            return blockState <= 0 ? Fluids.EMPTY.getDefaultState() : this.getFlowing(blockState, false);
        }
    }

    @Shadow
    protected abstract boolean isInfinite();

    @Shadow
    protected abstract boolean isMatchingAndStill(FluidState state);

    @Shadow
    protected abstract boolean receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState);

    @Shadow
    protected abstract int getLevelDecreasePerBlock(WorldView world);

    @Shadow
    public abstract FluidState getStill(boolean falling);

    @Shadow
    public abstract FluidState getFlowing(int level, boolean falling);

    @Shadow
    protected abstract int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState);

    @Shadow
    protected abstract void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state);
}
