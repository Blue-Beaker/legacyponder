package io.bluebeaker.legacyponder.manual.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.text.ITextComponent;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.regex.Pattern;

@ZenClass("mods.legacyponder.DrawableText")
@ZenRegister
public class DrawableText extends DrawableBase {

    public static final Pattern PATTERN = Pattern.compile("(§[0-9a-fr])");
    private final String text;
    private final int color;
    private boolean dropShadow = true;
    private int maxWidth = 0;
    private float hAlign = 0F;
    private float vAlign = 0F;
    private int xOffset = 0;
    private int yOffset = 0;

    public DrawableText(String text, int color){
        this.text = text;
        this.color = color;

        updateSizes();
    }

    /** Build a DrawableText with the specified text and color. The text will be formatted as a translation key, so it can be used for localization. If the text is not a valid translation key, it will be displayed as is.
     * @param text Translation key of the text to be displayed. If the text is not a valid translation key, it will be displayed as is.
     * @param color Color of the text, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The new DrawableText instance
     */
    @ZenMethod
    public static DrawableText buildFormatted(String text, int color, Object... parameters) {
        return new DrawableText(TextUtils.formatKey(text, parameters),color);
    }

    /** Build a DrawableText with the specified text/ITextComponent.
     * @param text Text to be displayed.
     * @param color Color of the text, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The new DrawableText instance
     */
    @ZenMethod
    public static DrawableText build(String text, int color) {
        return new DrawableText(text,color);
    }
    @ZenMethod
    public static DrawableText build(ITextComponent text, int color) {
        return new DrawableText(text.getFormattedText(),color);
    }

    @ZenMethod
    public DrawableText withShadow(boolean dropShadow){
        this.dropShadow=dropShadow;
        return this;
    }

    /** Set the horizontal alignment of the text.
     * @param alignFactor Horizontal alignment factor. 0 is left aligned, 0.5 is centered, and 1 is right aligned. Values outside of this range are allowed for more extreme alignments.
     * @return This
     */
    @ZenMethod
    public DrawableText setAlign(float alignFactor){
        this.hAlign =alignFactor;
        updateSizes();
        return this;
    }
    /** Set the vertical alignment of the text.
     * @param vAlignFactor Vertical alignment factor. 0 is top aligned, 0.5 is centered, and 1 is bottom aligned. Values outside of this range are allowed for more extreme alignments.
     * @return This
     */
    @ZenMethod
    public DrawableText setVAlign(float vAlignFactor){
        this.vAlign =vAlignFactor;
        updateSizes();
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {
        String text1 = getText();
        if(isLinkClickable() && isLastHovered()){
            // Add underline formatting when hovered and interactable to indicate that the text can be interacted with
            text1="§n"+ PATTERN.matcher(text1).replaceAll("$1§n");
        }
        FontRenderer fr = screen.mc.fontRenderer;
        RenderUtils.drawSplitString(fr,text1,x,y,maxWidth,color,dropShadow, hAlign, vAlign);
//        List<String> strings = RenderUtils.listFormattedStringToWidth(fr,text,maxWidth);
//        for (int i = 0; i < strings.size(); i++) {
//            fr.drawString(strings.get(i),x-(fr.getStringWidth(strings.get(i))*alignFactor),y+i*fr.FONT_HEIGHT,color,dropShadow);
//        }
//        RenderUtils.drawSplitString(screen.mc.fontRenderer, text1, x-xOffset,y,maxWidth,color,dropShadow);
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

    @ZenMethod
    public int getXMin() {return x-xOffset;}
    @ZenMethod
    public int getXMax(){return x-xOffset+w;}
    @ZenMethod
    public int getYMin() {return y-yOffset;}
    @ZenMethod
    public int getYMax(){return y-yOffset+h;}

    private void updateSizes(){
        String text1 = getText();
        int textWidth=0;
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        List<String> lines = RenderUtils.listFormattedStringToWidth(fr, text1, maxWidth);

        for (String line : lines) {
            textWidth = Math.max(textWidth, fr.getStringWidth(line));
        }

        this.w = textWidth;
        this.h = lines.size()*fr.FONT_HEIGHT;
        this.xOffset = Math.round(this.w * hAlign);
        this.yOffset = Math.round(this.h * vAlign);
    }

}
