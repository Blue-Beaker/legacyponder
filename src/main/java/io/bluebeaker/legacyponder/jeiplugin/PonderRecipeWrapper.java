package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class PonderRecipeWrapper implements IRecipeWrapper {
    private final List<ItemStack> items;
    private final List<FluidStack> fluids;
    private final IDrawableStatic slotDrawable;

    public PonderRecipeWrapper(IGuiHelper guiHelper, List<ItemStack> items, List<FluidStack> fluids) {
        this.items=items;
        this.fluids=fluids;
        this.slotDrawable = guiHelper.getSlotDrawable();
    }

    public static PonderRecipeWrapper createOnlyItems(IGuiHelper guiHelper, List<ItemStack> items) {
        return new PonderRecipeWrapper(guiHelper, items, Collections.emptyList());
    }

    public static PonderRecipeWrapper createOnlyFluids(IGuiHelper guiHelper, List<FluidStack> fluids) {
        return new PonderRecipeWrapper(guiHelper, Collections.emptyList(), fluids);
    }

    public List<ItemStack> getItems(){
        return this.items;
    }
    public List<FluidStack> getFluids(){
        return this.fluids;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, items);
        ingredients.setOutputs(VanillaTypes.ITEM, items);
        ingredients.setInputs(VanillaTypes.FLUID, fluids);
        ingredients.setOutputs(VanillaTypes.FLUID, fluids);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = 0;
        int yPos = slotDrawable.getHeight() + 4;
    }
}
