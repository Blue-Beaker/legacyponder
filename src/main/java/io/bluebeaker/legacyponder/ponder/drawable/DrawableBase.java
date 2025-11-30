package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.DrawableBase")
@ZenRegister
public abstract class DrawableBase {
    public int x;
    public int y;
    public int w;
    public int h;

    public int parentX = 0;
    public int parentY = 0;

    public DrawableBase(){
    }
    @ZenMethod
    public DrawableBase setPosition(int x, int y){
        this.x=x;
        this.y=y;
        return this;
    }
    @ZenMethod
    public DrawableBase setSize(int w, int h){
        this.w=w;
        this.h=h;
        return this;
    }

    public abstract void draw(GuiScreen screen, int mouseX, int mouseY);

    @ZenMethod
    public int getX() {return x;}
    @ZenMethod
    public int getY() {return y;}
    @ZenMethod
    public int getWidth(){return w;}
    @ZenMethod
    public int getHeight(){return h;}

    public BoundingBox2D getBoundingBox(){
        return new BoundingBox2D(getX(),getY(),getWidth(),getHeight());
    }

    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY){return false;}

    @ZenMethod
    public int getAbsX() {return x+parentX;}
    @ZenMethod
    public int getAbsY() {return y+parentY;}
}
