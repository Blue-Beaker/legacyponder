package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.DrawableBox")
@ZenRegister
public class DrawableBox extends DrawableBase {
    private final int color;
    public DrawableBox(int x1, int y1, int x2, int y2, int color){
        this.x=x1;
        this.y=y1;
        this.w=x2-x1;
        this.h=y2-y1;
        this.color = color;
    }
    public DrawableBox(int x1, int y1, int x2, int y2, Color color){
        this(x1,y1,x2,y2,color.getRGB());
    }

    @ZenMethod
    public static DrawableBox build(int x1, int y1, int x2, int y2, int color) {
        return new DrawableBox(x1,y1,x2,y2,color);
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        GuiUtils.drawGradientRect(0, x, y, x + w, 1, color,color);
        GuiUtils.drawGradientRect(0, x, y, 1, y + h, color,color);
        GuiUtils.drawGradientRect(0, x+w-1, y, x + w, y + h, color,color);
        GuiUtils.drawGradientRect(0, x, y+h-1, x + w, y + h, color,color);
    }
}
