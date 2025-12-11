package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.ponder.Entry;
import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ZenClass("mods.legacyponder.IPonderEntry")
@ZenRegister
public class IPonderEntry {
    public final Entry internal;

    public IPonderEntry(Entry internal){
        this.internal=internal;
    }

    /** Create an entry with title
     * @param title Title for the entry
     * @return The entry */
    @ZenMethod
    public static IPonderEntry createPonderEntry(String title){
        return createPonderEntry(title,"");
    }

    /** Create an entry with title and summary
     * @param title Title for the entry
     * @param summary Summary for the entry
     * @return The entry */
    @ZenMethod
    public static IPonderEntry createPonderEntry(String title,String summary){
        Entry ponderEntry = new Entry(title,summary);
        return new IPonderEntry(ponderEntry);
    }

    /** Add a page to the entry
     * @param page The page to be added */
    @ZenMethod
    public void addPage(PonderPageBase page){
        this.internal.addPage(page);
    }

    /** Associate items with the entry, displayed in one slot
     * @param stacks IItemStacks to associate with the entry */
    @ZenMethod
    public void addItems(List<IItemStack> stacks){
        this.internal.addItems(stacks.stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()));
    }
    /** Associate items with the entry, displayed in one slot
     * @param stacks IItemStacks to associate with the entry */
    @ZenMethod
    public void addItems(IItemStack[] stacks){
        this.internal.addItems(Arrays.stream(stacks).map(CraftTweakerMC::getItemStack).collect(Collectors.toList()));
    }

    /** Associate an item with the entry
     * @param stack IItemStack to associate with the entry */
    @ZenMethod
    public void addItem(IItemStack stack){
        ItemStack itemStack = CraftTweakerMC.getItemStack(stack);
        this.internal.addItem(itemStack);
    }

    /** Associate a fluid with the entry
     * @param stack ILiquidStack to associate with the entry */
    @ZenMethod
    public void addFluid(ILiquidStack stack){
        FluidStack stack1 = CraftTweakerMC.getLiquidStack(stack).copy();
        stack1.amount=1000;
        this.internal.addFluid(stack1);
    }

    /** Associate an IIngredient with the entry, displayed in one slot
     * Only items will be added, fluids will be ignored here
     * @param ingredient IIngredient to associate with the entry */
    @ZenMethod
    public void addIngredient(IIngredient ingredient){
        addItems(ingredient.getItems());
    }

    /** Associate an IIngredient with the entry, displayed in multiple slots
     * Only items will be added, fluids will be ignored here
     * @param ingredient IIngredient to associate with the entry */
    @ZenMethod
    public void addIngredientFlat(IIngredient ingredient){
        for (IItemStack item : ingredient.getItems()) {
            addItem(item);
        }
    }
}
