package io.bluebeaker.legacyponder.ponder.hover;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import net.minecraft.client.gui.GuiScreen;

public class GuiHoverComponent {
    public final HoverComponent internal;
    public DrawableBase drawable;
    public GuiHoverComponent(HoverComponent internal) {
        this.internal = internal;
    }
    public void draw(GuiScreen parent, int x, int y){
        if(drawable==null){
            drawable=internal.drawableSupplier.process(0,0);
        }
        drawable.setPosition(x,y);
        drawable.draw(parent,-1,-1);
    }
}
