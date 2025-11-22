package io.bluebeaker.legacyponder.ponder.drawable;

import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class DrawableText extends DrawableBase {
    private final String text;
    private final int color;

    public DrawableText(String text, int color){
        this.text = text;
        this.color = color;
    }
    public DrawableText(String text, Color color){
        this(text,color.getRGB());
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        screen.drawString(screen.mc.fontRenderer,text,x,y,color);
    }
}
