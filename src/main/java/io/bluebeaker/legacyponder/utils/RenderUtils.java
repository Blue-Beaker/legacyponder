package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void setViewPort(BoundingBox2D boundingBox){
        setViewPort(boundingBox.x,boundingBox.y,boundingBox.w,boundingBox.h);
    }
    public static void setViewPort(int x, int y, int w, int h){
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor(x*factor, y*factor, w*factor, h*factor);

//        GlStateManager.scale(scaledResolution.getScaleFactor(), scaledResolution.getScaleFactor(), 1);
//        GlStateManager.viewport(x*scaledResolution.getScaleFactor(),y*scaledResolution.getScaleFactor(),w*scaledResolution.getScaleFactor(),h*scaledResolution.getScaleFactor());
    }
    public static void endViewPort(){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
//        GlStateManager.viewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }
}
