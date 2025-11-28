package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.page.PonderPageDrawable;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageDrawable")
@ZenRegister
public class IPonderPageDrawable extends IPonderPage<PonderPageDrawable> {
    public IPonderPageDrawable(PonderPageDrawable internal) {
        super(internal);
    }
}
