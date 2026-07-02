package io.bluebeaker.legacyponder.manual.drawable.container;

import io.bluebeaker.legacyponder.ModZenRegister;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModZenRegister
@ZenClass("mods.legacyponder.DrawableBoxContainer")
public class DrawableBoxContainer extends DrawableGroup{
    protected int margin = 0;
    protected boolean reverse = false;
    protected boolean vertical = false;

    public DrawableBoxContainer(){
    }

    @Override
    public void updateSizes() {

        x1=0;
        y1=0;
        x2=0;
        y2=0;

        int x3 = 0;
        int y3 = 0;

        for (DrawableBase child : children) {
            if(!vertical) {
                child.setPosition(0,child.getY());
                child.setPosition(x3-child.getXMin(),child.getY());
                if(!reverse) {
                    x3 = child.getXMax()+margin;
                }else {
                    x3 = child.getXMin()-margin;
                }
            }else {
                child.setPosition(child.getX(),0);
                child.setPosition(child.getX(),y3-child.getYMin());
                if(!reverse) {
                    y3 = child.getYMax()+margin;
                }else {
                    y3 = child.getYMin()-margin;
                }
            }
            x1 = Math.min(x1,child.getXMin());
            y1 = Math.min(y1,child.getYMin());
            x2 = Math.max(x2,child.getXMax());
            y2 = Math.max(y2,child.getYMax());
        }

        w=x2-x1;
        h=y2-y1;
        if(this.parent instanceof DrawableContainer){
            ((DrawableContainer) this.parent).updateSizes();
        }
    }

    @ZenMethod
    public boolean isVertical() {
        return vertical;
    }

    @ZenMethod
    public DrawableBoxContainer setHorizontal() {
        this.vertical = false;
        return this;
    }
    @ZenMethod
    public DrawableBoxContainer setVertical() {
        this.vertical = true;
        return this;
    }
    @ZenMethod
    public DrawableBoxContainer setVertical(boolean vertical) {
        this.vertical = vertical;
        return this;
    }

    @ZenMethod
    public int getMargin() {
        return margin;
    }

    @ZenMethod
    public DrawableBoxContainer setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    @ZenMethod
    public boolean isReverse() {
        return reverse;
    }

    @ZenMethod
    public DrawableBoxContainer setReverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }
}
