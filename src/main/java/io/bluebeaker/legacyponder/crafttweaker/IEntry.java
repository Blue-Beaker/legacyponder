package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ZenClass("mods.legacyponder.IEntry")
@ZenRegister
public class IEntry {
    public final Entry internal;

    public IEntry(Entry internal){
        this.internal=internal;
    }

    /** Create an entry with title
     * @param title Title for the entry
     * @return The entry */
    @ZenMethod
    public static IEntry createEntry(String title){
        return createEntry(title,"");
    }

    /** Create an entry with title and summary
     * @param title Title for the entry
     * @param summary Summary for the entry
     * @return The entry */
    @ZenMethod
    public static IEntry createEntry(String title, String summary){
        Entry ponderEntry = new Entry(title,summary);
        return new IEntry(ponderEntry);
    }

    /** Add a page to the entry
     * @param page The page to be added */
    @ZenMethod
    public void addPage(PageBase page){
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

    /** Associate fluids with the entry
     * @param stacks ILiquidStack to associate with the entry */
    @ZenMethod
    public void addFluid(ILiquidStack... stacks){
        for (ILiquidStack stack : stacks) {
            FluidStack stack1 = CraftTweakerMC.getLiquidStack(stack).copy();
            stack1.amount=1000;
            this.internal.addFluid(stack1);
        }
    }

    /** Associate IIngredient with the entry, displayed in one slot per IIngredient parameter
     * Only items will be added, fluids will be ignored here
     * @param ingredients to associate with the entry */
    @ZenMethod
    public void addIngredient(IIngredient... ingredients){
        for (IIngredient ingredient : ingredients) {
            addItems(ingredient.getItems());
        }
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
