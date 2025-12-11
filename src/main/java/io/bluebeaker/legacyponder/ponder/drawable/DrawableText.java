package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.DrawableText")
@ZenRegister
public class DrawableText extends DrawableBase {
    private final String text;
    private final int color;
    private boolean dropShadow = true;
    private int maxWidth = 0;

    public DrawableText(String text, int color){
        this.text = text;
        this.color = color;
    }
    public DrawableText(String text, Color color){
        this(text,color.getRGB());
    }

    @ZenMethod
    public static DrawableText buildFormatted(String text, int color) {
        return new DrawableText(TextUtils.formatKey(text),color);
    }
    @ZenMethod
    public static DrawableText build(String text, int color) {
        return new DrawableText(text,color);
    }

    @ZenMethod
    public DrawableText withShadow(boolean dropShadow){
        this.dropShadow=dropShadow;
        return this;
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        if(maxWidth<=0){
            screen.mc.fontRenderer.drawString(text,x,y,color,dropShadow);
        }else {
            RenderUtils.drawSplitString(screen.mc.fontRenderer,text,x,y,maxWidth,color,dropShadow);
        }
    }

    /** Set the max width of the text. A maxWidth <= 0 is considered no limit, and maxWidth < 10 is ignored to prevent errors
     * @param w Max width of the text.
     * @return This
     */
    @ZenMethod
    public DrawableText setMaxWidth(int w){
        maxWidth=w;
        if(maxWidth>0 && maxWidth<10) maxWidth=0;
        return this;
    }
    @ZenMethod
    @Override
    public int getWidth() {
        if(maxWidth<=0) return Minecraft.getMinecraft().fontRenderer.getStringWidth(this.text);
        return maxWidth;
    }
    @ZenMethod
    @Override
    public int getHeight() {
        if(maxWidth<=0) return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        return Minecraft.getMinecraft().fontRenderer.getWordWrappedHeight(this.text,maxWidth);
    }
}
