package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.text.ITextComponent;
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

        updateSizes();
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

    public static DrawableText build(ITextComponent text, int color) {
        return new DrawableText(text.getFormattedText(),color);
    }

    @ZenMethod
    public DrawableText withShadow(boolean dropShadow){
        this.dropShadow=dropShadow;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        String text1 = getText();
        if(maxWidth<=0){
            screen.mc.fontRenderer.drawString(text1,x,y,color,dropShadow);
        }else {
            RenderUtils.drawSplitString(screen.mc.fontRenderer, text1,x,y,maxWidth,color,dropShadow);
        }
    }

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        setMaxWidth(w);
        return this;
    }

    /** Set the max width of the text. A maxWidth <= 0 is considered no limit, and maxWidth < 10 is ignored to prevent errors
     * @param w Max width of the text.
     * @return This
     */
    @ZenMethod
    public DrawableText setMaxWidth(int w){
        maxWidth=w;
        if(maxWidth>0 && maxWidth<10) maxWidth=0;
        updateSizes();
        return this;
    }


    private void updateSizes(){
        String text1 = getText();
        this.w = maxWidth<=0 ? Minecraft.getMinecraft().fontRenderer.getStringWidth(text1) : maxWidth;
        this.h = maxWidth<=0 ? Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT : Minecraft.getMinecraft().fontRenderer.getWordWrappedHeight(text1,maxWidth);
    }
}
