package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.PonderPageBase;
import io.bluebeaker.legacyponder.ponder.PonderPageStructure;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.PonderPageStructure")
@ZenRegister
public class IPonderPageStructure extends IPonderPage<PonderPageStructure> {
    public IPonderPageStructure(PonderPageStructure internal) {
        super(internal);
    }
}
