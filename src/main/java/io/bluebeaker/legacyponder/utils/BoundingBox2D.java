package io.bluebeaker.legacyponder.utils;

public class BoundingBox2D {
    public final int x;
    public final int y;
    public final int w;
    public final int h;

    public static final BoundingBox2D EMPTY = new BoundingBox2D(0,0,0,0);

    public BoundingBox2D(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public boolean contains(int x1, int y1){
        return x1>=x && x1<x+w && y1>=y && y1<y+h;
    }

    public boolean collidesWith(BoundingBox2D other){
        return other.x <= this.getX2() && other.y <= this.getY2() && this.x <= other.getX2() && this.y <= other.getY2();
    }

    public int getX2(){
        return x+w;
    }
    public int getY2(){
        return y+h;
    }
}
