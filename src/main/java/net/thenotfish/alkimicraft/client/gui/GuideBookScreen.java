package net.thenotfish.alkimicraft.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class GuideBookScreen extends Screen {
    protected GuideBookScreen(Text title) {
        super(title);
    }
}
