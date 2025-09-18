package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import io.bluebeaker.legacyponder.ponder.page.PonderPageDummy;
import io.bluebeaker.legacyponder.ponder.page.PonderPageStructure;
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
}
