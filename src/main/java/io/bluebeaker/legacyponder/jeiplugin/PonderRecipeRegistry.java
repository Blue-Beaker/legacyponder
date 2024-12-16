package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.IJeiHelpers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class PonderRecipeRegistry {

    public static List<PonderRecipeWrapper> getRecipes(final IJeiHelpers jeiHelpers){
        List<PonderRecipeWrapper> recipes = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Items.DIAMOND));
        items.add(new ItemStack(Blocks.DIAMOND_BLOCK));
        recipes.add(PonderRecipeWrapper.createOnlyItems(jeiHelpers.getGuiHelper(), items));

        List<FluidStack> fluids = new ArrayList<>();
        fluids.add(FluidRegistry.getFluidStack("water",1000));
        recipes.add(new PonderRecipeWrapper(jeiHelpers.getGuiHelper(), items, fluids));
        return recipes;
    }
}
