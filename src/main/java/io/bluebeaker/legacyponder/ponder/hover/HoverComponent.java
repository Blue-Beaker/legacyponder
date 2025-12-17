package io.bluebeaker.legacyponder.ponder.hover;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.HoverComponent")
@ZenRegister
public class HoverComponent {
    public final float x;
    public final float y;
    public final float z;
    public final IDrawableSupplier drawableSupplier;

    public HoverComponent(float x, float y, float z, IDrawableSupplier drawableSupplier){
        this.x=x;
        this.y=y;
        this.z=z;
        this.drawableSupplier=drawableSupplier;
    }

    @ZenMethod
    public static HoverComponent build(float x, float y, float z, IDrawableSupplier drawableSupplier){
        return new HoverComponent(x,y,z,drawableSupplier);
    }

    public GuiHoverComponent getGui(){
        return new GuiHoverComponent(this);
    }
}
