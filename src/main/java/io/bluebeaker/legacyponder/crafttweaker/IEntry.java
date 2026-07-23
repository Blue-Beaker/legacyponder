package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import io.bluebeaker.legacyponder.ModZenRegister;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.page.PageBase;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.List;


@ModZenRegister
@ZenClass("mods.legacyponder.IEntry")
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

    //----------Main Ingredients----------

    /** Associate items with the entry, displayed in one slot
     * @param stacks IItemStacks to associate with the entry */
    @ZenMethod
    public void addItems(List<IItemStack> stacks){
        this.getMainLink().addItems(stacks);
    }
    /** Associate items with the entry, displayed in one slot
     * @param stacks IItemStacks to associate with the entry */
    @ZenMethod
    public void addItems(IItemStack[] stacks){
        this.getMainLink().addItems(stacks);
    }

    /** Associate an item with the entry
     * @param stack IItemStack to associate with the entry */
    @ZenMethod
    public void addItem(IItemStack stack){
        this.getMainLink().addItem(stack);
    }

    /** Associate fluids with the entry
     * @param stacks ILiquidStack to associate with the entry */
    @ZenMethod
    public void addFluid(ILiquidStack... stacks){
        this.getMainLink().addFluid(stacks);
    }

    /** Associate IIngredient with the entry, displayed in one slot per IIngredient parameter
     * Only items will be added, fluids will be ignored here
     * @param ingredients to associate with the entry */
    @ZenMethod
    public void addIngredient(IIngredient... ingredients){
        this.getMainLink().addIngredient(ingredients);
    }

    /** Associate an IIngredient with the entry, displayed in multiple slots
     * Only items will be added, fluids will be ignored here
     * @param ingredient IIngredient to associate with the entry */
    @ZenMethod
    public void addIngredientFlat(IIngredient ingredient){
        this.getMainLink().addIngredientFlat(ingredient);
    }

    //----------Get IIngredientLink Objects----------

    @ZenMethod
    public IIngredientLink getMainLink(){
        return new IIngredientLink(internal.getMainLink());
    }

    @ZenMethod
    public IEntry setHideInJei(boolean hideInJei){
        _setHideInJei(hideInJei);
        return this;
    }

    @ZenSetter("hideInJEI")
    public void _setHideInJei(boolean hideInJei){
        internal.hideInJEI = hideInJei;
    }

    @ZenGetter("hideInJEI")
    @ZenMethod
    public boolean getHideInJei(){
        return internal.hideInJEI;
    }
}
