package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.ponder.Entry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.legacyponder.IPonderEntry")
@ZenRegister
public class IPonderEntry {
    public final Entry internal;

    public IPonderEntry(Entry internal){
        this.internal=internal;
    }

    @ZenMethod
    public static IPonderEntry createPonderEntry(String title){
        return createPonderEntry(title,"");
    }
    @ZenMethod
    public static IPonderEntry createPonderEntry(String title,String summary){
        Entry ponderEntry = new Entry(title,summary);
        return new IPonderEntry(ponderEntry);
    }

    @ZenMethod
    public void addPage(IPonderPage<?> page){
        this.internal.addPage(page.internal);
    }

    @ZenMethod
    public void addItem(IItemStack stack){
        ItemStack itemStack = CraftTweakerMC.getItemStack(stack);
        this.internal.addItem(itemStack);
    }

    @ZenMethod
    public void addFluid(ILiquidStack stack){
        FluidStack stack1 = CraftTweakerMC.getLiquidStack(stack).copy();
        stack1.amount=1000;
        this.internal.addFluid(stack1);
    }

    @ZenMethod
    public void addIngredient(IIngredient ingredient){
        for (IItemStack item : ingredient.getItems()) {
            addItem(item);
        }
        for (ILiquidStack fluid : ingredient.getLiquids()) {
            addFluid(fluid);
        }
    }
}
