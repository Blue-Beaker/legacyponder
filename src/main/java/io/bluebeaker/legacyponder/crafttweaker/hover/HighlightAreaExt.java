package io.bluebeaker.legacyponder.crafttweaker.hover;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import io.bluebeaker.legacyponder.manual.hover.HighlightArea;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModZenRegister
@ZenExpansion("mods.legacyponder.HighlightArea")
public class HighlightAreaExt {
    @ZenMethodStatic
    public static HighlightArea build(IBlockPos pos1){
        return HighlightArea.build(CraftTweakerMC.getBlockPos(pos1));
    }

    @ZenMethodStatic
    public static HighlightArea build(IBlockPos pos1, IBlockPos pos2){
        return HighlightArea.build(CraftTweakerMC.getBlockPos(pos1),CraftTweakerMC.getBlockPos(pos2));
    }
}
