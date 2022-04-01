package net.fabricmc.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.fabricmc.alkimicraft.blocks.JujubeTreeTop;
import net.fabricmc.alkimicraft.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class JujubeTreeFeature extends Feature<DefaultFeatureConfig> {
    public JujubeTreeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        if (structureWorldAccess.isAir(blockPos) && structureWorldAccess.getBlockState(blockPos.down()).isIn(BlockTags.DIRT)) {
            structureWorldAccess.setBlockState(blockPos, BlockInit.JUJUBE_TOP.getDefaultState(),2);
//            JujubeTreeTop.generate(structureWorldAccess, blockPos, random, 8, BlockInit.JUJUBE_LOG, BlockInit.JUJUBE_LEAVES);
            return true;
        } else {
            return false;
        }
    }
}
