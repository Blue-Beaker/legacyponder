package io.bluebeaker.legacyponder.manual.hover;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;

public class GuiHoverComponent {
    public final HoverComponent internal;
    private DrawableBase drawable;

    public int offX;
    public int offY;
    public GuiHoverComponent(HoverComponent internal) {
        this.internal = internal;
        this.offX =internal.offsetX;
        this.offY =internal.offsetY;
    }

    public void updatePosition(int x, int y){
        DrawableBase drawable = getDrawable();
        drawable.setPosition(x,y);
    }
    public void draw(GuiUnconfusion parent, int x, int y, int mouseX, int mouseY){
        DrawableBase drawable = getDrawable();
        drawable.draw(parent,mouseX,mouseY);
    }

    public DrawableBase getDrawable() {
        if(drawable==null){
            drawable=internal.drawableSupplier.process(0,0);
        }
        return drawable;
    }
}
