package io.bluebeaker.legacyponder.ponder.drawable;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.DrawableScroll")
public class DrawableScroll extends DrawableBase{
    public DrawableBase internal = null;
    public int scrollX = 0;
    public int scrollY = 0;

    public DrawableScroll(DrawableBase internal, int width, int height){
        this.internal=internal;
        this.internal.setParent(this);
        this.w=width;
        this.h=height;
    }

    @Override
    public int getOffsetX() {return x+scrollX;}
    @Override
    public int getOffsetY() {return y+scrollY;}

    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {
        BoundingBox2D bounds = screen.getPageBounds();
        RenderUtils.setViewPort(x+ bounds.x,y+ bounds.y,w,h);
        GlStateManager.translate(scrollX- bounds.x,scrollY- bounds.y,0);
        internal.draw(screen, mouseX-getOffsetX(), mouseY-getOffsetY());
        RenderUtils.endViewPort();
    }

    public void scrollBy(int x, int y){
        this.scrollX=scrollX-x;
        this.scrollY=scrollY-y;
        this.scrollX=Math.max(scrollX,w-internal.getXMax());
        this.scrollY=Math.max(scrollY,h-internal.getYMax());
        this.scrollX=Math.min(scrollX,internal.getXMin());
        this.scrollY=Math.min(scrollY,internal.getYMin());
        this.internal.updateParentPos();
    }


    @Override
    public boolean onMouseClick(GuiUnconfusion parent, int x, int y, int button) {
        if(this.internal==null) return false;
        return this.internal.onMouseClick(parent,x-getOffsetX(),y-getOffsetY(),button);
    }

    @Override
    public boolean onMouseRelease(GuiUnconfusion parent, int x, int y, int state) {
        if(this.internal==null) return false;
        return this.internal.onMouseRelease(parent,x-getOffsetX(),y-getOffsetY(),state);
    }

    @Override
    public void onKeyTyped(GuiUnconfusion parent, char typedChar, int keyCode) {
        if(this.internal==null) return;
        this.internal.onKeyTyped(parent,typedChar,keyCode);
        if(keyCode==Keyboard.KEY_RIGHT){
            this.scrollBy(10,0);
        }
        if(keyCode==Keyboard.KEY_LEFT){
            this.scrollBy(-10,0);
        }
        if(keyCode==Keyboard.KEY_UP){
            this.scrollBy(0,-10);
        }
        if(keyCode==Keyboard.KEY_DOWN){
            this.scrollBy(0,10);
        }
    }

    @Override
    public boolean onMouseHover(GuiUnconfusion screen, int mouseX, int mouseY) {
        if(!this.isInteractable() || this.internal==null){
            return false;
        }
        return internal.onMouseHover(screen, mouseX - getOffsetX(), mouseY - this.getOffsetY());
    }
}
