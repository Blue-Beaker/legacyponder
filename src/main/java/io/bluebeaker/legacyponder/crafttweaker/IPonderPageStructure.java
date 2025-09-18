package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.page.PonderPageStructure;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageStructure")
@ZenRegister
public class IPonderPageStructure extends IPonderPage<PonderPageStructure> {
    public IPonderPageStructure(PonderPageStructure internal) {
        super(internal);
    }
}
