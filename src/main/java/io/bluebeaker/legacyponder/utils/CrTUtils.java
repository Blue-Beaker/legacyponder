package io.bluebeaker.legacyponder.utils;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;

import java.util.ArrayList;
import java.util.List;

public class CrTUtils {
    public static List<IItemStack> unpackIngredients(IIngredient... ingredients){
        List<IItemStack> stacks = new ArrayList<>();
        for (IIngredient ingredient : ingredients) {
            stacks.addAll(ingredient.getItems());
        }
        return stacks;
    }
}
