package net.thenotfish.alkimicraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.thenotfish.alkimicraft.AlkimiCraft;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MicroscopeScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(AlkimiCraft.MOD_ID, "textures/gui/container/microscope.png");
    private static final Identifier BG_IMAGE = new Identifier(AlkimiCraft.MOD_ID, "textures/gui/container/microscope_image_bg.png");
    private static final int window_x = 83;
    private static final int window_y = 84;
    private static final int bg_x = 256;
    private static final int bg_y = 256;
    private static final int bg_start_x = 0;
    private static final int bg_start_y = 256;



    public MicroscopeScreen(ScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
        this.titleX = 0;
        this.titleY = 0;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 - 21;
        drawTexture(matrixStack, x, y, 0, 0, 176, 187, 512,512);
        System.out.println(i+"\t"+j);

//        int k = ((MicroscopeScreenHandler)this.handler).getMixProgress();
        drawTexture(matrixStack, x+86, y+7, Math.max(bg_start_x, Math.min(i-100, bg_start_x + bg_x -window_x)), Math.max(bg_start_y, Math.min(j+100, bg_start_y + bg_y-window_y)), window_x, window_y, 512, 512);
        drawTexture(matrixStack, x+86, y+7, 176, 0, 83, 84, 512, 512);
    }
}
