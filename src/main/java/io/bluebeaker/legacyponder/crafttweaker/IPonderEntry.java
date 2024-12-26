package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.legacyponder.IPonderEntry")
@ZenRegister
public class IPonderEntry {
    public final PonderEntry internal;

    public IPonderEntry(PonderEntry internal){
        this.internal=internal;
    }

    @ZenMethod
    public static IPonderEntry createPonderEntry(String id){
        PonderEntry ponderEntry = new PonderEntry(id);
        return new IPonderEntry(ponderEntry);
    }

    @ZenMethod
    public String getID(){
        return this.internal.id;
    }

    @ZenMethod
    public void addPage(IPonderPage page){
        this.internal.addPage(page.internal);
    }

    @ZenMethod
    public void addItem(IItemStack stack){
        this.internal.addItem(CraftTweakerMC.getItemStack(stack));
    }

    @ZenMethod
    public void addFluid(ILiquidStack stack){
        this.internal.addFluid(CraftTweakerMC.getLiquidStack(stack));
    }
}
