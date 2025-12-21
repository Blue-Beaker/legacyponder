package io.bluebeaker.legacyponder.ponder.hover;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import org.lwjgl.util.vector.Vector3f;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.HoverComponent")
@ZenRegister
public class HoverComponent {
    public final Vector3f pos;
    public int color = 0xFFFFFF;
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
        color = ((r & 0XFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
        return this;
    }

    public GuiHoverComponent getGui(){
        return new GuiHoverComponent(this);
    }
}
