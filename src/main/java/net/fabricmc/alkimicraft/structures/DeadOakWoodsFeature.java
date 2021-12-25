package net.fabricmc.alkimicraft.structures;

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

public class DeadOakWoodsFeature extends Feature<DefaultFeatureConfig> {
    Random random = new Random();
    public DeadOakWoodsFeature(Codec<DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        int num = random.nextInt(5) + 5;
        int diameter = random.nextInt(5) + 10;
        float down_rate = 0.5f;
        BlockPos center_pos = context.getOrigin();

        for (int n = 1; n <= num; n++) {
            BlockPos n_pos = center_pos.add(new Vec3i(random.nextInt(diameter)-diameter/2, 0, random.nextInt(diameter)-diameter/2));
            BlockPos n_topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, n_pos);
            int height = random.nextInt(4);

            BlockState blockState = context.getWorld().getBlockState(n_topPos.down()).getBlock().getDefaultState();
            if (!blockState.getFluidState().isIn(FluidTags.WATER)) {
                if (random.nextFloat() < down_rate) {
                    if (random.nextFloat() < 0.5) {
                        for (int y = -1; y <= height; y++) {
                            world.setBlockState(n_topPos.north(y), Blocks.OAK_LOG.getDefaultState().with(Properties.AXIS, Direction.Axis.Z), 3);
                        }
                    } else {
                        for (int y = -1; y <= height; y++) {
                            world.setBlockState(n_topPos.west(y), Blocks.OAK_LOG.getDefaultState().with(Properties.AXIS, Direction.Axis.X), 3);
                        }
                    }
                } else {
                    for (int y = -1; y <= height; y++) {
                        world.setBlockState(n_topPos.up(y), Blocks.OAK_LOG.getDefaultState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
