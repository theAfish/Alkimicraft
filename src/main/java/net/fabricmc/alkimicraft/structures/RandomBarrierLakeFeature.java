package net.fabricmc.alkimicraft.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Random;

public class RandomBarrierLakeFeature extends Feature<RandomBarrierLakeFeature.Config> {
    private static final BlockState CAVE_AIR;

    public RandomBarrierLakeFeature(Codec<RandomBarrierLakeFeature.Config> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<RandomBarrierLakeFeature.Config> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        RandomBarrierLakeFeature.Config config = context.getConfig();
        if (blockPos.getY() <= structureWorldAccess.getBottomY() + 4) {
            return false;
        } else {
            blockPos = blockPos.down(4);
//            if (!structureWorldAccess.getStructures(ChunkSectionPos.from(blockPos), StructureFeature.VILLAGE).isEmpty()) {
//                return false;
//            } else {
            boolean[] bls = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
                double d = random.nextDouble() * 6.0D + 3.0D;
                double e = random.nextDouble() * 4.0D + 2.0D;
                double f = random.nextDouble() * 6.0D + 3.0D;
                double g = random.nextDouble() * (16.0D - d - 2.0D) + 1.0D + d / 2.0D;
                double h = random.nextDouble() * (8.0D - e - 4.0D) + 2.0D + e / 2.0D;
                double k = random.nextDouble() * (16.0D - f - 2.0D) + 1.0D + f / 2.0D;

                for(int l = 1; l < 15; ++l) {
                    for(int m = 1; m < 15; ++m) {
                        for(int n = 1; n < 7; ++n) {
                            double o = ((double)l - g) / (d / 2.0D);
                            double p = ((double)n - h) / (e / 2.0D);
                            double q = ((double)m - k) / (f / 2.0D);
                            double r = o * o + p * p + q * q;
                            if (r < 1.0D) {
                                bls[(l * 16 + m) * 8 + n] = true;
                            }
                        }
                    }
                }
            }

            BlockState j = config.fluid().getBlockState(random, blockPos);

            int s;
            boolean bl;
            int d;
            int e;
            for(d = 0; d < 16; ++d) {
                for(s = 0; s < 16; ++s) {
                    for(e = 0; e < 8; ++e) {
                        bl = !bls[(d * 16 + s) * 8 + e] && (d < 15 && bls[((d + 1) * 16 + s) * 8 + e] || d > 0 && bls[((d - 1) * 16 + s) * 8 + e] || s < 15 && bls[(d * 16 + s + 1) * 8 + e] || s > 0 && bls[(d * 16 + (s - 1)) * 8 + e] || e < 7 && bls[(d * 16 + s) * 8 + e + 1] || e > 0 && bls[(d * 16 + s) * 8 + (e - 1)]);
                        if (bl) {
                            Material f = structureWorldAccess.getBlockState(blockPos.add(d, e, s)).getMaterial();
                            if (e >= 4 && f.isLiquid()) {
                                return false;
                            }

                            if (e < 4 && !f.isSolid() && structureWorldAccess.getBlockState(blockPos.add(d, e, s)) != j) {
                                return false;
                            }
                        }
                    }
                }
            }

            boolean f;
            for(d = 0; d < 16; ++d) {
                for(s = 0; s < 16; ++s) {
                    for(e = 0; e < 8; ++e) {
                        if (bls[(d * 16 + s) * 8 + e]) {
                            BlockPos bll = blockPos.add(d, e, s);
                            if (this.canReplace(structureWorldAccess.getBlockState(bll))) {
                                f = e >= 4;
                                structureWorldAccess.setBlockState(bll, f ? CAVE_AIR : j, 2);
                                if (f) {
                                    structureWorldAccess.createAndScheduleBlockTick(bll, CAVE_AIR.getBlock(), 0);
                                    this.markBlocksAboveForPostProcessing(structureWorldAccess, bll);
                                }
                            }
                        }
                    }
                }
            }

            BlockState barrier = config.barrier().getBlockState(random, blockPos);
            BlockState addon = config.addon().getBlockState(random, blockPos);
            if (!barrier.isAir()) {
                for(s = 0; s < 16; ++s) {
                    for(e = 0; e < 16; ++e) {
                        for(int bll = 0; bll < 8; ++bll) {
                            f = !bls[(s * 16 + e) * 8 + bll] && (s < 15 && bls[((s + 1) * 16 + e) * 8 + bll] || s > 0 && bls[((s - 1) * 16 + e) * 8 + bll] || e < 15 && bls[(s * 16 + e + 1) * 8 + bll] || e > 0 && bls[(s * 16 + (e - 1)) * 8 + bll] || bll < 7 && bls[(s * 16 + e) * 8 + bll + 1] || bll > 0 && bls[(s * 16 + e) * 8 + (bll - 1)]);
                            if (f && (bll < 4 || random.nextInt(2) != 0)) {
                                BlockState blockState = structureWorldAccess.getBlockState(blockPos.add(s, bll, e));
                                if (blockState.getMaterial().isSolid() && !blockState.isIn(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                    BlockPos g = blockPos.add(s, bll, e);
                                    if (random.nextInt(10) < 7){
                                        structureWorldAccess.setBlockState(g, barrier, 2);
                                    } else {
                                        structureWorldAccess.setBlockState(g, addon, 2);
                                    }
                                    this.markBlocksAboveForPostProcessing(structureWorldAccess, g);
                                }
                            }
                        }
                    }
                }
            }

            if (j.getFluidState().isIn(FluidTags.WATER)) {
                for(s = 0; s < 16; ++s) {
                    for(e = 0; e < 16; ++e) {
                        bl = true;
                        BlockPos blockPos4 = blockPos.add(s, 4, e);
                        if ((structureWorldAccess.getBiome(blockPos4).comp_349()).canSetIce(structureWorldAccess, blockPos4, false) && this.canReplace(structureWorldAccess.getBlockState(blockPos4))) {
                            structureWorldAccess.setBlockState(blockPos4, Blocks.ICE.getDefaultState(), 2);
                        }
                    }
                }
            }

            return true;

        }
    }

    private boolean canReplace(BlockState state) {
        return !state.isIn(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    static {
        CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    }

    public static record Config(BlockStateProvider fluid, BlockStateProvider barrier, BlockStateProvider addon) implements FeatureConfig {
        public static final Codec<RandomBarrierLakeFeature.Config> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("fluid").forGetter(RandomBarrierLakeFeature.Config::fluid), BlockStateProvider.TYPE_CODEC.fieldOf("barrier").forGetter(RandomBarrierLakeFeature.Config::barrier), BlockStateProvider.TYPE_CODEC.fieldOf("addon").forGetter(RandomBarrierLakeFeature.Config::addon)).apply(instance, RandomBarrierLakeFeature.Config::new);
        });

        public Config(BlockStateProvider fluid, BlockStateProvider barrier, BlockStateProvider addon) {
            this.fluid = fluid;
            this.barrier = barrier;
            this.addon = addon;
        }

        public BlockStateProvider fluid() {
            return this.fluid;
        }

        public BlockStateProvider barrier() {
            return this.barrier;
        }

        public BlockStateProvider addon() {
            return this.addon;
        }
    }
}
