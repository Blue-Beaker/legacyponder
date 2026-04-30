package io.bluebeaker.legacyponder.manual.drawable;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableScroll")
public class DrawableScroll extends DrawableContainer{
    public DrawableBase internal = null;
    public int scrollX = 0;
    public int scrollY = 0;

    public DrawableScroll(int width, int height){
        this.w=width;
        this.h=height;
    }

    @ZenMethod
    public static DrawableScroll build(DrawableBase internal, int width, int height){
        return new DrawableScroll(width, height).setChild(internal);
    }

    @ZenMethod
    public DrawableScroll setChild(DrawableBase internal){
        this.internal=internal;
        internal.setParent(this);
        return this;
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

    public boolean scrollX(int x){
        int lastScrollX=this.scrollX;
        if(this.internal.getWidth()<=this.w) this.scrollX=internal.getXMin();
        this.scrollX=scrollX-x;
        this.scrollX=Math.max(scrollX,w-internal.getXMax());
        this.scrollX=Math.min(scrollX,internal.getXMin());
        this.internal.updateParentPos();
        return lastScrollX!=this.scrollX;
    }
    public boolean scrollY(int y){
        int lastScrollY=this.scrollY;
        if(this.internal.getHeight()<=this.h) this.scrollY=internal.getYMin();
        this.scrollY=scrollY-y;
        this.scrollY=Math.max(scrollY,h-internal.getYMax());
        this.scrollY=Math.min(scrollY,internal.getYMin());
        this.internal.updateParentPos();
        return lastScrollY!=this.scrollY;
    }

    @Override
    public boolean onMouseScroll(GuiUnconfusion parent, int mouseX, int mouseY, int wheelDelta) {
        boolean shiftDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
        int wheel = -(int) Math.ceil((double) wheelDelta / 12);

        if(shiftDown){
            if(this.scrollX(wheel))
                return true;
        }else {
            if(this.scrollY(wheel))
                return true;
        }
        return internal.onMouseScroll(parent, mouseX, mouseY, wheelDelta);
    }

    @Nullable
    @Override
    DrawableBase getFocusedChild() {
        return internal;
    }

    @Override
    protected List<DrawableBase> getChildren() {
        return internal==null ? Collections.emptyList() : Collections.singletonList(internal);
    }

    @Override
    public boolean onKeyTyped(GuiUnconfusion parent, char typedChar, int keyCode) {
        if(this.internal==null) return false;
        boolean scroll = false;
        if(keyCode==Keyboard.KEY_RIGHT){
            scroll |= this.scrollX(10);
        } else if(keyCode==Keyboard.KEY_LEFT){
            scroll |= this.scrollX(-10);
        } else if(keyCode==Keyboard.KEY_UP){
            scroll |= this.scrollY(-10);
        } else if(keyCode==Keyboard.KEY_DOWN){
            scroll |= this.scrollY(10);
        }
        if (scroll) return true;
        return this.internal.onKeyTyped(parent,typedChar,keyCode);
    }

    @Override
    public boolean onMouseHover(GuiUnconfusion screen, int mouseX, int mouseY) {
        if(!this.isInteractable() || this.internal==null){
            return false;
        }
        return internal.onMouseHover(screen, mouseX - this.getOffsetX(), mouseY - this.getOffsetY());
    }
}
