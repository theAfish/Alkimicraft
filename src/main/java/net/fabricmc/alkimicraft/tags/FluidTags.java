package net.fabricmc.alkimicraft.tags;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FluidTags {
    public static final Tag<Fluid> SEWAGE = TagRegistry.fluid(new Identifier("alkimicraft", "sewage"));
}
