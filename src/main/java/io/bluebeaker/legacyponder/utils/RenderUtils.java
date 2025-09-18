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
}
