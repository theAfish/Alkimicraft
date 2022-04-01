package net.fabricmc.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class IronRootFeature  extends Feature<DefaultFeatureConfig> {
    public IronRootFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos center = context.getOrigin();

        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        int quantity = random.nextInt(5);

        for (int i = 0; i < quantity; ++i){
            BlockPos blockPos = center.offset(Direction.Axis.X, random.nextInt(-2,2)).offset(Direction.Axis.Z, random.nextInt(-2,2));
            BlockPos downPos = blockPos.down();
            BlockPos downDownPos = downPos.down();
            if (world.getBlockState(blockPos).isAir()){
                if (world.getBlockState(downPos).isOf(Blocks.STONE) || world.getBlockState(downPos).isOf(Blocks.COBBLESTONE)){
                    world.setBlockState(downPos, BlockInit.IRON_COBBLESTONE.getDefaultState(),2);
                    world.setBlockState(blockPos, BlockInit.IRON_ROOT_GRASS.getDefaultState().with(Properties.AGE_7, 7), 2);
                }else if (world.getBlockState(downDownPos).isOf(Blocks.STONE) || world.getBlockState(downPos).isOf(Blocks.COBBLESTONE)){
                    world.setBlockState(downDownPos, BlockInit.IRON_COBBLESTONE.getDefaultState(),2);
                    world.setBlockState(downPos, BlockInit.IRON_ROOT_GRASS.getDefaultState().with(Properties.AGE_7, 7), 2);
                }
            }
        }
        return true;
    }
}
