package net.thenotfish.alkimicraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.MathHelper;
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
    private boolean mouseClicked;
    private float drag_x;
    private float drag_y;
    private final float drag_rate = 0.5f;
    private static final int window_x = 83;
    private static final int window_y = 84;
    private static final int bg_x = 256;
    private static final int bg_y = 256;
    private static final int bg_start_x = 0;
    private static final int bg_start_y = 256;
    private static final int init_window_x = 80;
    private static final int init_window_y = 83;



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
        int offsetx = (int)(this.drag_x);
        int offsety = (int)(this.drag_y);
        drawTexture(matrixStack, x, y, 0, 0, 176, 187, 512,512);

//        int k = ((MicroscopeScreenHandler)this.handler).getMixProgress();
        drawTexture(matrixStack, x+86, y+7, MathHelper.clamp(bg_start_x+init_window_x + offsetx, bg_start_x, bg_start_x + bg_x -window_x), MathHelper.clamp(bg_start_y+init_window_y + offsety, bg_start_y,bg_start_y + bg_y-window_y), window_x, window_y, 512, 512);
        drawTexture(matrixStack, x+86, y+7, 176, 0, 83, 84, 512, 512);
    }

    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        // f : x offset; g: y offset
        if (this.mouseClicked) {
            this.drag_x += (float)f*drag_rate;
            this.drag_x = MathHelper.clamp(this.drag_x, -(bg_x-10f)/2, (bg_x-36f)/2);
            this.drag_y += (float)g*drag_rate;
            this.drag_y = MathHelper.clamp(this.drag_y, -(bg_y-10f)/2, (bg_y-36f)/2);
            return true;
        } else {
            return super.mouseDragged(d, e, i, f, g);
        }
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        this.mouseClicked = true;
        return super.mouseClicked(d, e, i);
    }
}
