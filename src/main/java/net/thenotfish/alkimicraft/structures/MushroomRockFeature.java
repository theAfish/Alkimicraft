package net.thenotfish.alkimicraft.structures;

import com.mojang.serialization.Codec;
import net.thenotfish.alkimicraft.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class MushroomRockFeature extends Feature<DefaultFeatureConfig> {
    public MushroomRockFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();

        StructureWorldAccess structureWorldAccess;
        for(structureWorldAccess = context.getWorld(); structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2; blockPos = blockPos.down()) {
        }

        if (!structureWorldAccess.getBlockState(blockPos).isOf(Blocks.SAND)) {
            return false;
        } else {
            blockPos = blockPos.up(random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                blockPos = blockPos.up(10 + random.nextInt(30));
            }

            int k;
            int l;
            for(k = 0; k < i; ++k) {
                float f = (1.0F - (float)k / (float)i) * (float)j;
                l = MathHelper.ceil(f);

                for(int m = -l; m <= l; ++m) {
                    float g = (float)MathHelper.abs(m) - 0.25F;

                    for(int n = -l; n <= l; ++n) {
                        float h = (float)MathHelper.abs(n) - 0.25F;
                        if ((m == 0 && n == 0 || !(g * g + h * h > f * f)) && (m != -l && m != l && n != -l && n != l || !(random.nextFloat() > 0.75F))) {
                            BlockState blockState = structureWorldAccess.getBlockState(blockPos.add(m, k, n));
                            if (blockState.isAir() || isSoil(blockState) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.STONE)) {
                                this.setBlockState(structureWorldAccess, blockPos.add(m, k, n), BlockInit.SEDIMENTARY_ROCK.getDefaultState());
                            }

                            if (k != 0 && l > 1) {
                                blockState = structureWorldAccess.getBlockState(blockPos.add(m, -k, n));
                                if (blockState.isAir() || isSoil(blockState) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.STONE)) {
                                    this.setBlockState(structureWorldAccess, blockPos.add(m, -k, n), BlockInit.SEDIMENTARY_ROCK.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }

            k = j - 1;
            if (k < 0) {
                k = 0;
            } else if (k > 1) {
                k = 1;
            }

            for(int f = -k; f <= k; ++f) {
                for(l = -k; l <= k; ++l) {
                    BlockPos m = blockPos.add(f, -1, l);
                    int g = 50;
                    if (Math.abs(f) == 1 && Math.abs(l) == 1) {
                        g = random.nextInt(5);
                    }

                    while(m.getY() > 50) {
                        BlockState n = structureWorldAccess.getBlockState(m);
                        if (!n.isAir() && !isSoil(n) && !n.isOf(Blocks.SAND) && !n.isOf(BlockInit.SEDIMENTARY_ROCK) && !n.isOf(Blocks.STONE)) {
                            break;
                        }

                        this.setBlockState(structureWorldAccess, m, BlockInit.SEDIMENTARY_ROCK.getDefaultState());
                        m = m.down();
                        --g;
                        if (g <= 0) {
                            m = m.down(random.nextInt(5) + 1);
                            g = random.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}
