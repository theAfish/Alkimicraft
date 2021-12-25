package net.fabricmc.alkimicraft.blocks;

import net.minecraft.state.property.EnumProperty;

public class BlockProperties {
    public static final EnumProperty<LoggableFluidsEnum> fluidLogged = EnumProperty.of("fluid_logged", LoggableFluidsEnum.class);
}
