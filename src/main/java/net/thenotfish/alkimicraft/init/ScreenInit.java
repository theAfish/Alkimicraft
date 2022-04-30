package net.thenotfish.alkimicraft.init;

import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.screen.MicroscopeScreenHandler;
import net.thenotfish.alkimicraft.screen.WoodenBarrelScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenInit {
    public static final ScreenHandlerType<WoodenBarrelScreenHandler> WOODEN_BARREL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AlkimiCraft.MOD_ID, "wooden_barrel"), WoodenBarrelScreenHandler::new);
    public static final ScreenHandlerType<MicroscopeScreenHandler> MICROSCOPE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AlkimiCraft.MOD_ID, "microscope"), MicroscopeScreenHandler::new);


    public static void init(){
    }
}
