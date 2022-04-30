package net.thenotfish.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PillarFeature extends Feature<DefaultFeatureConfig> {
    Random random = new Random();
    public final BlockState pillarBlockState;
    public final int maxHeight;

    public PillarFeature(Codec<DefaultFeatureConfig> config, BlockState pillarBlockState, int maxHeight) {
        super(config);
        this.pillarBlockState = pillarBlockState;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos center_pos = context.getOrigin();

        BlockPos n_topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, center_pos);
        int height = random.nextInt(maxHeight);
        BlockState blockState = context.getWorld().getBlockState(n_topPos.down()).getBlock().getDefaultState();
        if (!blockState.getFluidState().isIn(FluidTags.WATER)) {
            for (int y = -1; y <= height; y++) {
                world.setBlockState(n_topPos.up(y), pillarBlockState, 3);
            }
        }
        return true;
    }
}
