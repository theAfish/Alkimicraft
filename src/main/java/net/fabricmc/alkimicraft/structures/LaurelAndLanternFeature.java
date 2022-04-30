package net.fabricmc.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.StructureInit;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class LaurelAndLanternFeature extends Feature<DefaultFeatureConfig> {
    public LaurelAndLanternFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> featureContext) {
        StructureWorldAccess world = featureContext.getWorld();
        BlockPos blockPos = featureContext.getOrigin();
        Random random = featureContext.getRandom();
        int flowerNum = random.nextInt(3,10);
        int radius = 5;
        if (world.isAir(blockPos) && world.getBlockState(blockPos.down()).isIn(BlockTags.DIRT)){
            StructureInit.STAR_LAUREL.generate(world, featureContext.getGenerator(), random, blockPos);
            for (int i = 0; i < flowerNum; i++){
                BlockPos flowerPos = blockPos.offset(Direction.Axis.X, random.nextInt(-radius,radius)).offset(Direction.Axis.Z, random.nextInt(-radius,radius));
                if (world.isAir(flowerPos) && world.getBlockState(flowerPos.down()).isIn(BlockTags.DIRT)){
                    world.setBlockState(flowerPos, BlockInit.SNOW_LANTERN_PLANT.getDefaultState(),3);
                }else if(world.isAir(flowerPos.down()) && world.getBlockState(flowerPos.down(2)).isIn(BlockTags.DIRT)){
                    world.setBlockState(flowerPos.down(), BlockInit.SNOW_LANTERN_PLANT.getDefaultState(),3);
                }else if(world.isAir(flowerPos.up()) && world.getBlockState(flowerPos).isIn(BlockTags.DIRT)){
                    world.setBlockState(flowerPos.up(), BlockInit.SNOW_LANTERN_PLANT.getDefaultState(),3);
                }
            }
            return true;
        }else {
            return false;
        }
    }
}
