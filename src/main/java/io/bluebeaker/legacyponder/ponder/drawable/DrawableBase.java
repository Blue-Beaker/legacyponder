package io.bluebeaker.legacyponder.ponder.drawable;

import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;

public abstract class DrawableBase {
    public int x;
    public int y;
    public int w;
    public int h;

    public DrawableBase(){
    }

    public DrawableBase setPosition(int x, int y){
        this.x=x;
        this.y=y;
        return this;
    }
    public DrawableBase setSize(int w, int h){
        this.w=w;
        this.h=h;
        return this;
    }

    public abstract void draw(GuiScreen screen, int mouseX, int mouseY);

    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth(){return w;}
    public int getHeight(){return h;}

    public BoundingBox2D getBoundingBox(){
        return new BoundingBox2D(getX(),getY(),getWidth(),getHeight());
    }
}
