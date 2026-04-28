package io.bluebeaker.legacyponder.manual.hover;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.awt.*;

public class GuiHoverComponent {
    public final HoverComponent internal;
    private DrawableBase drawable;
    public float lineX;
    public float lineY;

    public int offX;
    public int offY;
    public GuiHoverComponent(HoverComponent internal) {
        this.internal = internal;

        this.offX =internal.offsetX;
        this.offY =internal.offsetY;
    }

    public void updatePos(float x, float y){
        this.lineX=x;
        this.lineY=y;

        int hoverX = Math.round(x+offX);
        int hoverY = Math.round(y+offY);
        this.updateDrawablePos(hoverX,hoverY);
    }
    public void updateDrawablePos(int x, int y){
        DrawableBase drawable = getDrawable();
        drawable.setPosition(x,y);
    }
    public void draw(GuiUnconfusion parent, int mouseX, int mouseY){
        DrawableBase drawable = getDrawable();
        drawable.draw(parent,mouseX,mouseY);
    }

    public DrawableBase getDrawable() {
        if(drawable==null){
            drawable=internal.drawableSupplier.process(0,0);
        }
        return drawable;
    }

    public Vec2i getLineEnd(){
        int x1 = getDrawable().getXMin();
        int y1 = getDrawable().getYMin();
        int x2 = getDrawable().getXMax();
        int y2 = getDrawable().getYMax();
        return new Vec2i((x1+x2)/2,(y1+y2)/2);
    }

    public void drawBackground(){
        int x1 = this.getDrawable().getXMin();
        int y1 = this.getDrawable().getYMin();
        int x2 = this.getDrawable().getXMax();
        int y2 = this.getDrawable().getYMax();

        drawHoverBackground(this.internal.getColor(), x1-3, y1-3, x2+3, y2+3, (int) lineX, (int) lineY);
    }

    public static void drawHoverBackground(Color color, int x1, int y1, int x2, int y2, int baseX, int baseY) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int rgb = color.getRGB();

        int col3 = (int)(r*0.2F)<<16 | (int)(g*0.2F)<<8 | (int)(b*0.2F) | 0x80000000;


        //Line
//        int lineEndX=(x1+x2)/2;
//        int lineEndY=(y1+y2)/2;
        int lineEndX = Math.max(x1,Math.min(x2-1,baseX));
        int lineEndY = Math.max(y1,Math.min(y2-1,baseY));

        RenderUtils.drawLine(baseX +1, baseY +1,lineEndX+1,lineEndY+1,col3);
        RenderUtils.drawLine(baseX, baseY,lineEndX,lineEndY,rgb);
        
        //Shadow
        GuiUtils.drawGradientRect(0, x1 +2, y2-1, x2, y2+1, col3, col3);
        GuiUtils.drawGradientRect(0, x2, y1 +2, x2+1, y2, col3, col3);

        // Box
        // Top
        GuiUtils.drawGradientRect(0, x1 +1, y1, x2-1, y1 +1, rgb, rgb);
        // Bottom
        GuiUtils.drawGradientRect(0, x1 +1, y2-1, x2-1, y2, rgb, rgb);
        // Left
        GuiUtils.drawGradientRect(0, x1, y1 +1, x1 +1, y2-1, rgb, rgb);
        // Right
        GuiUtils.drawGradientRect(0, x2-1, y1 +1, x2, y2-1, rgb, rgb);

        int col2 = (int)(r*0.2F)<<16 | (int)(g*0.2F)<<8 | (int)(b*0.2F) | 0xFF000000;


        GuiUtils.drawGradientRect(0, x1 +1, y1 +1, x2-1, y2-1, col2,col2);
    }

}
