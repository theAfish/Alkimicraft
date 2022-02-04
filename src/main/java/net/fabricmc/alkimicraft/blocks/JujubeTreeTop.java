package net.fabricmc.alkimicraft.blocks;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JujubeTreeTop extends AbstractThinTreeTop {
    public static final BooleanProperty BOTTOM;

    public JujubeTreeTop(Settings settings) {
        super(BlockInit.JUJUBE_LOG, BlockInit.JUJUBE_LEAVES, BlockInit.SANDY_LOAM, settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(BOTTOM, true));
    }

    protected void grow(World world, BlockPos pos, int age) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, age).with(BOTTOM, false), 2);
//        world.syncWorldEvent(1033, pos, 0);
    }

    protected void die(World world, BlockPos pos) {
        world.setBlockState(pos, this.getDefaultState().with(AGE, 5).with(BOTTOM, false), 2);
//        world.syncWorldEvent(1034, pos, 0);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, BOTTOM);
    }

    static {
        BOTTOM = Properties.BOTTOM;
    }
}
