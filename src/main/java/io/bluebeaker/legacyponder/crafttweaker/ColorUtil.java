package io.bluebeaker.legacyponder.crafttweaker;
import io.bluebeaker.legacyponder.ModZenRegister;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ModZenRegister
@ZenClass("mods.legacyponder.ColorUtil")
public class ColorUtil {
    @ZenMethod
    public static int packRGB(int r, int g, int b){
        return new Color(r,g,b).getRGB();
    }
    @ZenMethod
    public static int packRGBA(int r, int g, int b, int a){
        return new Color(r,g,b,a).getRGB();
    }
}
