package net.fabricmc.alkimicraft.tags;

import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidTags {
    public static final TagKey<Fluid> SEWAGE = TagKey.of(Registry.FLUID_KEY, new Identifier("alkimicraft", "sewage"));
}
