package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
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

    public static void drawSplitString(FontRenderer fontRenderer, String text, int x, int y, int wrapWidth, int color, boolean dropShadow){
        String[] split = text.replace("\\n","\n").split("\n");
        List<String> strings = new ArrayList<>();
        if(wrapWidth==0){
            Collections.addAll(strings, split);
        }else {
            for (String s : split) {
                strings.addAll(fontRenderer.listFormattedStringToWidth(s, wrapWidth));
            }
        }
        for (int i = 0; i < strings.size(); i++) {
            fontRenderer.drawString(strings.get(i),x,y+i*fontRenderer.FONT_HEIGHT,color,dropShadow);
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
}
