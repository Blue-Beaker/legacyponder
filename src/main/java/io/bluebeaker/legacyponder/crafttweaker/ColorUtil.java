package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.ColorUtil")
@ZenRegister
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
