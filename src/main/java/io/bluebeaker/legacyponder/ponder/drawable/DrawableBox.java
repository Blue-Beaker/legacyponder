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

    /** Builds a box drawable with the specified coordinates and color.
     * @param x1 left X coordinate
     * @param y1 top Y coordinate
     * @param x2 right X coordinate
     * @param y2 bottom Y coordinate
     * @param color color of the box in ARGB format (e.g. 0xFFFF0000 for opaque red)
     * @return The new DrawableBox instance
     */
    @ZenMethod
    public static DrawableBox build(int x1, int y1, int x2, int y2, int color) {
        return new DrawableBox(x1,y1,x2,y2,color);
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        int color1 = color;
        if(isClickable() && isLastHovered()){
            Color color2 = new Color(color1);
            float[] hsb = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
            hsb[1] = Math.min(1, hsb[1]-0.2f);
            hsb[2] = Math.min(1, hsb[2]+0.2f);
            color1 = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        }
        GuiUtils.drawGradientRect(0, x, y, x + w, 1, color1, color1);
        GuiUtils.drawGradientRect(0, x, y, 1, y + h, color1, color1);
        GuiUtils.drawGradientRect(0, x+w-1, y, x + w, y + h, color1, color1);
        GuiUtils.drawGradientRect(0, x, y+h-1, x + w, y + h, color1, color1);
    }
}
