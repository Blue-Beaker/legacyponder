package io.bluebeaker.legacyponder.crafttweaker.drawable;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.drawable.DrawableFluid;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModZenRegister
@ZenExpansion("mods.legacyponder.DrawableFluid")
public class DrawableFluidExt {
    @ZenMethodStatic
    public static DrawableFluid build(ILiquidStack fluid) {
        return new DrawableFluid(CraftTweakerMC.getLiquidStack(fluid));
    }
}
