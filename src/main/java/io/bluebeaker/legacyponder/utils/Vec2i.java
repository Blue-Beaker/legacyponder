package io.bluebeaker.legacyponder.utils;

public class Vec2i {
    public int x = 0;
    public int y = 0;

    public Vec2i(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Vec2i(Vec2i vec){
        this(vec.x, vec.y);
    }

    public Vec2i add(int x2, int y2){
        return new Vec2i(x+x2,y+y2);
    }
    public Vec2i sub(int x2, int y2){
        return new Vec2i(x-x2,y-y2);
    }
    public Vec2i add(Vec2i vec2){
        return add(vec2.x,vec2.y);
    }
    public Vec2i sub(Vec2i vec2){
        return sub(vec2.x,vec2.y);
    }
}
