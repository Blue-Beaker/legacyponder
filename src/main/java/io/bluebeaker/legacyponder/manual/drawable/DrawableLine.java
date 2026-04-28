package io.bluebeaker.legacyponder.manual.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.gui.GuiScreen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableLine")
@ZenRegister
public class DrawableLine extends DrawableBase {
    private final int color;

    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    protected List<Vec2i> points = new ArrayList<>();

    public DrawableLine(int color){
        this.color = color;
    }

    /** Create a line with two points included initially.
     * @param x1 X coordinate of the first point
     * @param y1 Y coordinate of the first point
     * @param x2 X coordinate of the second point
     * @param y2 Y coordinate of the second point
     * @param color Color of the line, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The created DrawableLine instance.
     */
    @ZenMethod
    public static DrawableLine build(int x1, int y1, int x2, int y2, int color) {
        return new DrawableLine(color).point(x1,y1).point(x2,y2);
    }

    /** Create a line without any point included initially.
     * @param color Color of the line, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The created DrawableLine instance.
     */
    @ZenMethod
    public static DrawableLine build(int color) {
        return new DrawableLine(color);
    }

    /** Add a point to the line. The line will be drawn between each adjacent points in the order they were added.
     * @param x X coordinate of the point
     * @param y Y coordinate of the point
     * @return The DrawableLine instance, for chaining calls.
     */
    @ZenMethod
    public DrawableLine point(int x, int y){
        points.add(new Vec2i(x,y));
        updateSizes();
        return this;
    }

    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {

        for (int i = 1; i < points.size(); i++) {
            Vec2i start = points.get(i - 1);
            Vec2i end = points.get(i);
            RenderUtils.drawLine(start.x, start.y, end.x, end.y, color);
        }

    }

    @Override
    public boolean isFocused(GuiScreen screen, int mouseX, int mouseY) {
        return false;
    }


    private void updateSizes(){
        x1=0;
        y1=0;
        x2=0;
        y2=0;
        for (Vec2i point : points) {
            x1 =Math.min(x1,point.x);
            y1 =Math.min(y1,point.y);
            x2 =Math.max(x2,point.x+1);
            y2 =Math.max(y2,point.y+1);
        }
        w=x2-x1;
        h=y2-y1;
    }

    @ZenMethod
    @Override
    public int getXMin() {return x+ x1;}
    @ZenMethod
    @Override
    public int getYMin() {return y+ y1;}
    @ZenMethod
    @Override
    public int getXMax(){return x+ x2;}
    @ZenMethod
    @Override
    public int getYMax(){return y+ y2;}

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        return this;
    }
}
