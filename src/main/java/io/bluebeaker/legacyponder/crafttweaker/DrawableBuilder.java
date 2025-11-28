package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableItem;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableText;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableTexture;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.DrawableBuilder")
@ZenRegister
public class DrawableBuilder {
    @ZenMethod
    public static DrawableItem buildItem(IItemStack item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableText buildText(String text, int color){
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
