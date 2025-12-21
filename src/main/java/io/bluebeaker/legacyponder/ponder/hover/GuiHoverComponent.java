package io.bluebeaker.legacyponder.ponder.hover;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import net.minecraft.client.gui.GuiScreen;

public class GuiHoverComponent {
    public final HoverComponent internal;
    private DrawableBase drawable;
    public GuiHoverComponent(HoverComponent internal) {
        this.internal = internal;
    }
    public void draw(GuiScreen parent, int x, int y){
        DrawableBase drawable = getDrawable();
        drawable.setPosition(x,y);
        drawable.draw(parent,x-1,y-1);
    }

    public DrawableBase getDrawable() {
        if(drawable==null){
            drawable=internal.drawableSupplier.process(0,0);
        }
        return drawable;
    }
}
