package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderUtils {
    public static void setViewPort(BoundingBox2D boundingBox){
        setViewPort(boundingBox.x,boundingBox.y,boundingBox.w,boundingBox.h);
    }
    public static void setViewPort(int x, int y, int w, int h){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,0);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor(x*factor, Minecraft.getMinecraft().displayHeight-y*factor-h*factor, w*factor, h*factor);
    }
    public static void endViewPort(){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    public static void drawSplitString(FontRenderer fontRenderer, String text, int x, int y, int wrapWidth, int color){
        drawSplitString(fontRenderer,text,x,y,wrapWidth,color,true);
    }

    public static List<String> listFormattedStringToWidth(FontRenderer fr, String text, int wrapWidth){
        String[] split = TextUtils.splitLines(text);
        List<String> strings = new ArrayList<>();
        if(wrapWidth!=0 && wrapWidth<10){
            wrapWidth=10; // Prevent stack overflow
        }
        if(wrapWidth==0){
            Collections.addAll(strings, split);
        }else {
            for (String s : split) {
                strings.addAll(fr.listFormattedStringToWidth(s, wrapWidth));
            }
        }
        return strings;
    }

    public static void drawSplitString(FontRenderer fr, String text, int x, int y, int wrapWidth, int color, boolean dropShadow){
        drawSplitString(fr, text, x, y, wrapWidth, color, dropShadow, 0, 0);
    }
    public static void drawSplitString(FontRenderer fr, String text, int x, int y, int wrapWidth, int color, boolean dropShadow, float hAlign, float vAlign){
        List<String> strings = listFormattedStringToWidth(fr,text,wrapWidth);
        float drawY = y - (vAlign*strings.size()*fr.FONT_HEIGHT);
        for (int i = 0; i < strings.size(); i++) {
            float drawX = x;
            if(hAlign!=0){
                drawX = x - (fr.getStringWidth(strings.get(i))*hAlign);
            }
            fr.drawString(strings.get(i),drawX,drawY+i*fr.FONT_HEIGHT,color,dropShadow);
        }
    }

    public static void drawHighlightBox(Vec3d pos1, Vec3d pos2, Color color){
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float fighting_fix = 0.01f;
        double x1= pos1.x-fighting_fix;
        double y1= pos1.y-fighting_fix;
        double z1= pos1.z-fighting_fix;
        double x2= pos2.x+fighting_fix;
        double y2= pos2.y+fighting_fix;
        double z2= pos2.z+fighting_fix;

        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        // Line
        bufferbuilder.pos(x1,y1,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y1,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y2,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y2,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y1,z1).color(r, g, b,255).endVertex();

        bufferbuilder.pos(x1,y1,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y1,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y1,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y1,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y2,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y2,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x2,y2,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y2,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y2,z1).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y2,z2).color(r, g, b,255).endVertex();
        bufferbuilder.pos(x1,y1,z2).color(r, g, b,255).endVertex();

        tessellator.draw();
    }

    public static void drawLine(int startX, int startY, int endX, int endY, int color) {
        // Generalized Bresenham's line algorithm with run-length optimization
        int x = startX;
        int y = startY;
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        int sx = startX < endX ? 1 : -1;// step in x direction
        int sy = startY < endY ? 1 : -1;// step in y direction
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
            if (x == endX && y == endY) {
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
