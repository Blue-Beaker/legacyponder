package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.client.gui.GuiScreen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.DrawableText")
@ZenRegister
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

    @ZenMethod
    public static DrawableText build(String text, int color) {
        return new DrawableText(text,color);
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        screen.drawString(screen.mc.fontRenderer,text,x,y,color);
    }
}
