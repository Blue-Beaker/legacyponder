package io.bluebeaker.legacyponder.ponder.hover;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import org.lwjgl.util.vector.Vector3f;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.HoverComponent")
@ZenRegister
public class HoverComponent {
    public final Vector3f pos;
    protected Color color = new Color(80,0,255);

    public final IDrawableSupplier drawableSupplier;

    public HoverComponent(float x, float y, float z, IDrawableSupplier drawableSupplier){
        this.pos=new Vector3f(x, y, z);
        this.drawableSupplier=drawableSupplier;
    }

    @ZenMethod
    public static HoverComponent build(float x, float y, float z, IDrawableSupplier drawableSupplier){
        return new HoverComponent(x,y,z,drawableSupplier);
    }
    @ZenMethod
    public HoverComponent setColor(int r, int g, int b){
        this.color = new Color(r,g,b);
        return this;
    }

    public GuiHoverComponent getGui(){
        return new GuiHoverComponent(this);
    }

    public Color getColor() {
        return color;
    }
}
