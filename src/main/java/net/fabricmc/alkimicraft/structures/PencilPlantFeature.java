package net.fabricmc.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.fabricmc.alkimicraft.blocks.PencilPlantTop;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PencilPlantFeature extends Feature<DefaultFeatureConfig> {
    public PencilPlantFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        if (structureWorldAccess.isAir(blockPos) && structureWorldAccess.getBlockState(blockPos.down()).isOf(Blocks.SAND)) {
//            structureWorldAccess.setBlockState(blockPos, BlockInit.JUJUBE_TOP.getDefaultState(),2);
            structureWorldAccess.setBlockState(blockPos.down(), BlockInit.LOAMY_SAND.getDefaultState(),2);
            PencilPlantTop.generate(structureWorldAccess, blockPos, random, 2);
            return true;
        } else {
            return false;
        }
    }
}
