package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.crafttweaker.PonderRegistry;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PonderRecipeRegistry {

    public static List<PonderRecipeWrapper> getRecipes(final IJeiHelpers jeiHelpers){
        List<PonderRecipeWrapper> recipes = new ArrayList<>();
        for(Map.Entry<String, PonderEntry> entry: PonderRegistry.getEntries().entrySet()){
            PonderEntry internal = entry.getValue();
            recipes.add(new PonderRecipeWrapper(jeiHelpers.getGuiHelper(),entry.getKey(),internal.getItems(),internal.getFluids()));
        }
//        List<ItemStack> items = new ArrayList<>();
//        items.add(new ItemStack(Items.DIAMOND));
//        items.add(new ItemStack(Blocks.DIAMOND_BLOCK));
//        recipes.add(PonderRecipeWrapper.createOnlyItems(jeiHelpers.getGuiHelper(), items));
//
//        List<FluidStack> fluids = new ArrayList<>();
//        fluids.add(FluidRegistry.getFluidStack("water",1000));
//        recipes.add(new PonderRecipeWrapper(jeiHelpers.getGuiHelper(), items, fluids));
        return recipes;
    }
}
