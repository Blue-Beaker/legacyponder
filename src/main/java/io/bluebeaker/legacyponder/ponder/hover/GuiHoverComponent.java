package io.bluebeaker.legacyponder.ponder.hover;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import net.minecraft.client.gui.GuiScreen;

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
    public void draw(GuiScreen parent, int x, int y, int mouseX, int mouseY){
        DrawableBase drawable = getDrawable();
        drawable.setPosition(x,y);
        drawable.draw(parent,mouseX,mouseY);
    }

    public DrawableBase getDrawable() {
        if(drawable==null){
            drawable=internal.drawableSupplier.process(0,0);
        }
        return drawable;
    }
}
