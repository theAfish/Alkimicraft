package net.fabricmc.alkimicraft.init;

import net.fabricmc.alkimicraft.AlkimiCraft;
import net.fabricmc.alkimicraft.screen.WoodenBarrelScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenInit {
    public static final ScreenHandlerType<WoodenBarrelScreenHandler> WOODEN_BARREL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AlkimiCraft.MOD_ID, "wooden_barrel"), WoodenBarrelScreenHandler::new);

    public static void init(){
    }
}
