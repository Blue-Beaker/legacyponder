package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.page.PonderPageGui;
import io.bluebeaker.legacyponder.ponder.page.PonderPageStructure;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageGUI")
@ZenRegister
public class IPonderPageGUI extends IPonderPage<PonderPageGui> {
    public IPonderPageGUI(PonderPageGui internal) {
        super(internal);
    }
}
