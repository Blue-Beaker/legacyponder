package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.PonderPageBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPage")
@ZenRegister
public class IPonderPage {
    public final PonderPageBase internal;

    public IPonderPage(PonderPageBase internal){
        this.internal=internal;
    }

}
