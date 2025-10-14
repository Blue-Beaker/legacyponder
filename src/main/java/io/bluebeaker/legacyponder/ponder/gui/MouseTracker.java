package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.utils.Vec2i;
import org.lwjgl.input.Mouse;

public class MouseTracker {

    public static final MouseTracker INSTANCE = new MouseTracker();

    int x;
    int y;
    int lastX;
    int lastY;
    int wheel;
    public void tick(){
        lastX=x;
        lastY=y;
        x=Mouse.getEventX();
        y=Mouse.getEventY();
        wheel+=Mouse.getEventDWheel();
    }
    public int getWheel(){
        int lastWheel = wheel;
        wheel=0;
        return lastWheel;
    }
    public Vec2i getMouseDelta(){
        return new Vec2i(x-lastX,y-lastY);
    }
}
