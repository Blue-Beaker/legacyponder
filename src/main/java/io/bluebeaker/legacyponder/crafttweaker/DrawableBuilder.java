package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.text.ITextComponent;
import io.bluebeaker.legacyponder.ponder.drawable.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Conventional builder class for creating drawable elements
 */
@ZenClass("mods.legacyponder.DrawableBuilder")
@ZenRegister
public class DrawableBuilder {

    @ZenMethod
    public static DrawableBox buildBox(int x1, int y1, int x2, int y2, int color){
        return DrawableBox.build(x1, y1, x2, y2, color);
    }

    @ZenMethod
    public static DrawableLine buildLine(int color){
        return DrawableLine.build(color);
    }
    @ZenMethod
    public static DrawableLine buildLine(int x1, int y1, int x2, int y2, int color){
        return DrawableLine.build(x1, y1, x2, y2, color);
    }

    @ZenMethod
    public static DrawableItem buildItem(IItemStack item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableItem buildItem(IIngredient item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableItem buildItem(IItemStack[] item){
        return DrawableItem.build(item);
    }

    @ZenMethod
    public static DrawableText formattedText(String text, int color){
        return DrawableText.buildFormatted(text, color);
    }
    @ZenMethod
    public static DrawableText buildText(String text, int color){
        return DrawableText.build(text, color);
    }
    @ZenMethod
    public static DrawableText buildText(ITextComponent text, int color){
        return DrawableText.build(text, color);
    }

    @ZenMethod
    public static DrawableTexture buildTexture(String texture, int x, int y, int w, int h){
        return DrawableTexture.build(texture,x,y,w,h);
    }
    @ZenMethod
    public static DrawableGroup buildGroup(){
        return new DrawableGroup();
    }
}
