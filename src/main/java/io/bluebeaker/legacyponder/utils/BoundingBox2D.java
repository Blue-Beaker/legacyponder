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
}
