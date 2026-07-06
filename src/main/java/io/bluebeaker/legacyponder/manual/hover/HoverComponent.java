package io.bluebeaker.legacyponder.manual.hover;
import io.bluebeaker.legacyponder.ModZenRegister;

import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import org.lwjgl.util.vector.Vector3f;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.awt.*;

@ModZenRegister
@ZenClass("mods.legacyponder.HoverComponent")
public class HoverComponent {
    public final Vector3f pos;
    protected Color color = new Color(80,0,255);

    /**
     * Used to bind this to DrawableHoverPos
     */
    protected int id = -1;

    public final IDrawableSupplier drawableSupplier;
    @ZenProperty
    public int offsetX=60;
    @ZenProperty
    public int offsetY=-10;

    @ZenProperty
    public float alignX=0;
    @ZenProperty
    public float alignY=0;

    public HoverComponent(float x, float y, float z, IDrawableSupplier drawableSupplier){
        this.pos=new Vector3f(x, y, z);
        this.drawableSupplier=drawableSupplier;
    }

    @ZenMethod
    public static HoverComponent build(float x, float y, float z, IDrawableSupplier drawableSupplier){
        return new HoverComponent(x,y,z,drawableSupplier);
    }

    @ZenMethod
    public HoverComponent setDefaultOffset(int offsetX, int offsetY){
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        return this;
    }
    /** Sets alignment factor (anchor point) for this component.
     * @param alignX Alignment X, 0.0=left
     * @param alignY Alignment Y, 0.0=top
     * @return this
     */
    @ZenMethod
    public HoverComponent setAlignFactor(float alignX, float alignY){
        this.alignX=alignX;
        this.alignY=alignY;
        return this;
    }

    @ZenMethod
    public HoverComponent setColor(int rgb){
        this.color = new Color(rgb);
        return this;
    }
    @ZenMethod
    public HoverComponent setColor(int r, int g, int b){
        this.color = new Color(r,g,b);
        return this;
    }
    @ZenMethod
    public HoverComponent setID(int id){
        this.id=id;
        return this;
    }
    public int getID(){
        return id;
    }

    public GuiHoverComponent getGui(){
        return new GuiHoverComponent(this);
    }

    public Color getColor() {
        return color;
    }
}
