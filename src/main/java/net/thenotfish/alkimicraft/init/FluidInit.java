package net.thenotfish.alkimicraft.init;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.fluid.FluidToxicSewage;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidInit {

    // Fluid
    public static final FlowableFluid TOXIC_SEWAGE_STILL = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage"), new FluidToxicSewage.Still());
    public static final FlowableFluid TOXIC_SEWAGE_FLOW = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "toxic_sewage_flow"), new FluidToxicSewage.Flowing());

//    public static final FlowableFluid SALTY_WATER_STILL = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "salty_water"), new FluidSaltyWater.Still());
//    public static final FlowableFluid SALTY_WATER_FLOW = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "salty_water_flow"), new FluidSaltyWater.Flowing());
//
//    public static final FlowableFluid FRESH_WATER_STILL = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "fresh_water"), new FluidFreshWater.Still());
//    public static final FlowableFluid FRESH_WATER_FLOW = Registry.register(Registry.FLUID, new Identifier(AlkimiCraft.MOD_ID, "fresh_water_flow"), new FluidFreshWater.Flowing());

    public static void init(){

    }
}
