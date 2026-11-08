package io.bluebeaker.legacyponder.crafttweaker.drawable;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.drawable.DrawableItem;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.Arrays;

@ModZenRegister
@ZenExpansion("mods.legacyponder.DrawableItem")
public class DrawableItemExt {
    /** Builds a drawable item from the given IItemStack, array of IItemStack, or IIngredient. If the item parameter contains multiple items, all of them will be included in the drawable and displayed in a cycle.
     * @param item the IItemStack, array of IItemStack or IIngredient to be displayed as a drawable item
     * @return A DrawableItem instance representing the given item(s)
     */
    @ZenMethodStatic
    public static DrawableItem build(IItemStack item) {
        return new DrawableItem(CraftTweakerMC.getItemStack(item));
}
    @ZenMethodStatic
    public static DrawableItem build(IItemStack[] items) {
        return new DrawableItem(Arrays.asList(CraftTweakerMC.getItemStacks(items)));
    }
    @ZenMethodStatic
    public static DrawableItem build(IIngredient item) {
        return build(item.getItemArray());
    }
}
