package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.page.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.PonderPage")
@ZenRegister
public class IPonderPage<T extends PonderPageBase> {
    public final T internal;

    public IPonderPage(T internal){
        this.internal=internal;
    }
    @ZenMethod
    public static IPonderPage<PonderPageDummy> dummyPage(){
        return new IPonderPage<>(new PonderPageDummy());
    }
    @ZenMethod
    public static IPonderPage<PonderPageDummy> dummyPage(String text){
        return new IPonderPage<>(new PonderPageDummy(text));
    }

    @ZenMethod
    public static IPonderPageStructure fromStructure(String id){
        return new IPonderPageStructure(new PonderPageStructure(id));
    }
    @ZenMethod
    public static IPonderPageGUI fromGUI(String texture,int x,int y,int w,int h){
        return new IPonderPageGUI(new PonderPageGui(texture,x,y,w,h));
    }
    @ZenMethod
    public static IPonderPageDrawable fromDrawable(IDrawableSupplier drawableSupplier){
        return new IPonderPageDrawable(new PonderPageDrawable(drawableSupplier));
    }
}
