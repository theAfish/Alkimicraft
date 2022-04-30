//
//  Copied from https://github.com/GoogeTan/FluidAPI/blob/1.17.X-fabric/src/main/java/ru/zahara/fluidapi/block/LoggableFluidsEnum.java
//

package net.thenotfish.alkimicraft.blocks;

import net.thenotfish.alkimicraft.init.FluidInit;
import net.thenotfish.alkimicraft.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.StringIdentifiable;

import java.util.Optional;

public enum LoggableFluidsEnum implements StringIdentifiable {
    EMPTY(null, "empty", 0)
        {
            @Override
            public ItemStack getBucket() {
                return new ItemStack(Items.BUCKET);
            }

            @Override
            public boolean canFillWith(BlockState state) {
                return false;
            }
        },
    LAVA(Fluids.LAVA, "lava",8)
        {
            @Override
            public ItemStack getBucket() {
                return new ItemStack(Items.LAVA_BUCKET);
            }

            @Override
            public boolean canFillWith(BlockState state) {
                return !state.getMaterial().isBurnable();
            }
        },
    TOXIC_SEWAGE(FluidInit.TOXIC_SEWAGE_STILL, "toxic_sewage",8)
        {
            @Override
            public ItemStack getBucket() {
                return new ItemStack(ItemInit.TOXIC_SEWAGE_BUCKET);
            }

            @Override
            public boolean canFillWith(BlockState state) {
                return true;
            }
        },
    WATER(Fluids.WATER, "water",8)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.WATER_BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER1(Fluids.FLOWING_WATER, "flowing_water1",1)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER2(Fluids.FLOWING_WATER, "flowing_water2",2)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER3(Fluids.FLOWING_WATER, "flowing_water3",3)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER4(Fluids.FLOWING_WATER, "flowing_water4",4)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER5(Fluids.FLOWING_WATER, "flowing_water5",5)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER6(Fluids.FLOWING_WATER, "flowing_water6",6)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            },
    FLOWING_WATER7(Fluids.FLOWING_WATER, "flowing_water7",7)
            {
                @Override
                public ItemStack getBucket() {
                    return new ItemStack(Items.BUCKET);
                }

                @Override
                public boolean canFillWith(BlockState state) {
                    return true;
                }
            };


    public final Optional<FlowableFluid> contains;
    public final String name;
    public final Integer height;

    LoggableFluidsEnum(FlowableFluid contains, String name, Integer height)
    {
        this.contains = Optional.ofNullable(contains);
        this.name = name;
        this.height = height;
    }

    public static boolean canFillWithFluid(BlockState state, Fluid fluid)
    {
        return fluid == Fluids.LAVA || fluid == FluidInit.TOXIC_SEWAGE_STILL
                || fluid == Fluids.WATER
                || fluid == Fluids.FLOWING_WATER;

//        return getByFluid(fluid).isAllowed.test(state);
    }

    public static LoggableFluidsEnum getByFluid(Fluid fluid, Integer height)
    {
        if (fluid == Fluids.LAVA) {
            return LAVA;
        }else if (fluid == FluidInit.TOXIC_SEWAGE_STILL) {
            return TOXIC_SEWAGE;
        }else if (fluid == Fluids.WATER) {
            return WATER;
        }else if (fluid == Fluids.FLOWING_WATER) {
            return switch (height) {
                case 1 -> FLOWING_WATER1;
                case 2 -> FLOWING_WATER2;
                case 3 -> FLOWING_WATER3;
                case 4 -> FLOWING_WATER4;
                case 5 -> FLOWING_WATER5;
                case 6 -> FLOWING_WATER6;
                case 7 -> FLOWING_WATER7;
                default -> EMPTY;
            };
        } else {
            return EMPTY;
        }
    }

    public static boolean hasFluid(Fluid fluid)
    {
        return getByFluid(fluid, 8) != EMPTY;
    }

    public abstract ItemStack getBucket();

    public boolean canFillWith(BlockState state)
    {
        return true;
    }

    @Override
    public String asString() {
        return name;
    }
}
