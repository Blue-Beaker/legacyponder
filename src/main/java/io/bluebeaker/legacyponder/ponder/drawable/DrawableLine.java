package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.DrawableLine")
@ZenRegister
public class DrawableLine extends DrawableBase {
    private final int color;
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;

    public DrawableLine(int x1,int y1, int x2, int y2, int color){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;

        this.x=Math.min(x1,x2);
        this.y=Math.min(y1,y2);
        this.w=Math.abs(x2-x1);
        this.h=Math.abs(y2-y1);

        this.color = color;
    }
    public DrawableLine(int x1,int y1, int x2, int y2, Color color){
        this(x1,y1,x2,y2,color.getRGB());
    }

    @ZenMethod
    public static DrawableLine build(int x1,int y1, int x2, int y2, int color) {
        return new DrawableLine(x1,y1,x2,y2,color);
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        // Generalized Bresenham's line algorithm with run-length optimization
        int x = x1;
        int y = y1;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;// step in x direction
        int sy = y1 < y2 ? 1 : -1;// step in y direction
        int err = dx - dy;

        // runType: 0 = none(single), 1 = horizontal, 2 = vertical
        int runType = 0;
        int runStartX = 0, runStartY = 0;
        int prevX = 0, prevY = 0;
        boolean first = true;

        while (true) {
            if (first) {
                runStartX = x;
                runStartY = y;
                first = false;
            } else {
                // Check the relationship between (x,y) and (prevX,prevY)
                if (x == prevX && Math.abs(y - prevY) == 1) {
                    // Y
                    if (runType == 0) runType = 2;
                    else if (runType == 1) {
                        // Refresh horizontal run (to prev)
                        int left = Math.min(runStartX, prevX);
                        int right = Math.max(runStartX, prevX) + 1;
                        int top = prevY;
                        GuiUtils.drawGradientRect(0, left, top, right, top + 1, color, color);
                        runStartX = prevX;
                        runStartY = prevY;
                        runType = 2;
                    }
                } else if (y == prevY && Math.abs(x - prevX) == 1) {
                    // X
                    if (runType == 0) runType = 1;
                    else if (runType == 2) {
                        // Refresh vertical run (to prev)
                        int top = Math.min(runStartY, prevY);
                        int bottom = Math.max(runStartY, prevY) + 1;
                        int left = prevX;
                        GuiUtils.drawGradientRect(0, left, top, left + 1, bottom, color, color);
                        runStartX = prevX;
                        runStartY = prevY;
                        runType = 1;
                    }
                } else {
                    // Diagonal or non-adjacent point, flush current run
                    if (runType == 1) {
                        int left = Math.min(runStartX, prevX);
                        int right = Math.max(runStartX, prevX) + 1;
                        int top = prevY;
                        GuiUtils.drawGradientRect(0, left, top, right, top + 1, color, color);
                    } else if (runType == 2) {
                        int top = Math.min(runStartY, prevY);
                        int bottom = Math.max(runStartY, prevY) + 1;
                        int left = prevX;
                        GuiUtils.drawGradientRect(0, left, top, left + 1, bottom, color, color);
                    } else {
                        GuiUtils.drawGradientRect(0, prevX, prevY, prevX + 1, prevY + 1, color, color);
                    }
                    runStartX = x;
                    runStartY = y;
                    runType = 0;
                }
            }

            prevX = x;
            prevY = y;

            // Reached end point
            if (x == x2 && y == y2) {
                if (runType == 1) {
                    int left = Math.min(runStartX, prevX);
                    int right = Math.max(runStartX, prevX) + 1;
                    int top = prevY;
                    GuiUtils.drawGradientRect(0, left, top, right, top + 1, color, color);
                } else if (runType == 2) {
                    int top = Math.min(runStartY, prevY);
                    int bottom = Math.max(runStartY, prevY) + 1;
                    int left = prevX;
                    GuiUtils.drawGradientRect(0, left, top, left + 1, bottom, color, color);
                } else {
                    GuiUtils.drawGradientRect(0, prevX, prevY, prevX + 1, prevY + 1, color, color);
                }
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }
}
