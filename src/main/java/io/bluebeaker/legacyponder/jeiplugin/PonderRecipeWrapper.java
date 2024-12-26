package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.Collections;
import java.util.List;

public class PonderRecipeWrapper implements IRecipeWrapper {
    private final List<ItemStack> items;
    private final List<FluidStack> fluids;
    private final IDrawableStatic slotDrawable;
    public final String id;
    public static final String BUTTON_TRANSLATION_KEY = "button.legacyponder.open";

    private GuiButtonExt button = new GuiButtonExt(0, (160 - 100) / 2, 10, 100, 20, I18n.format(BUTTON_TRANSLATION_KEY));

    public PonderRecipeWrapper(IGuiHelper guiHelper,String id , List<ItemStack> items, List<FluidStack> fluids) {
        this.id=id;
        this.items=items;
        this.fluids=fluids;
        this.slotDrawable = guiHelper.getSlotDrawable();
    }

    public static PonderRecipeWrapper createOnlyItems(IGuiHelper guiHelper,String id, List<ItemStack> items) {
        return new PonderRecipeWrapper(guiHelper,id, items, Collections.emptyList());
    }

    public static PonderRecipeWrapper createOnlyFluids(IGuiHelper guiHelper,String id, List<FluidStack> fluids) {
        return new PonderRecipeWrapper(guiHelper,id, Collections.emptyList(), fluids);
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

        button.displayString = I18n.format(BUTTON_TRANSLATION_KEY);
        button.x = (recipeWidth - button.width) / 2;
        button.y = button.height / 2 + 36;
        button.drawButton(minecraft, mouseX, mouseY, 1);
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if(button.mousePressed(minecraft,mouseX,mouseY)){
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenPonder());
            return true;
        }else{
            return false;
        }
    }
}
