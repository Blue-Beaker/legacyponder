package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.HistoryTracker;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class UnconfusionRecipeWrapper implements IRecipeWrapper {
    private final List<List<ItemStack>> items;
    private final List<FluidStack> fluids;
    public final String id;
    public static final String BUTTON_TRANSLATION_KEY = "button.legacyponder.open";
    private final List<String> lines;

    private final GuiButtonExt button = new GuiButtonExt(0, (160 - 100) / 2, 10, 100, 20, I18n.format(BUTTON_TRANSLATION_KEY));

    public UnconfusionRecipeWrapper(IGuiHelper guiHelper, String id , List<List<ItemStack>> items, List<FluidStack> fluids) {
        this(guiHelper,id,items,fluids,Collections.emptyList());
    }
    public UnconfusionRecipeWrapper(IGuiHelper guiHelper, String id , List<List<ItemStack>> items, List<FluidStack> fluids, List<String> lines) {
        this.id=id;
        this.items=items;
        this.fluids=fluids;
        this.lines = lines;
    }

    public static UnconfusionRecipeWrapper createOnlyItems(IGuiHelper guiHelper, String id, List<List<ItemStack>> items, List<String> lines) {
        return new UnconfusionRecipeWrapper(guiHelper,id, items, Collections.emptyList(), lines);
    }

    public static UnconfusionRecipeWrapper createOnlyFluids(IGuiHelper guiHelper, String id, List<FluidStack> fluids, List<String> lines) {
        return new UnconfusionRecipeWrapper(guiHelper,id, Collections.emptyList(), fluids, lines);
    }

    public List<List<ItemStack>> getItems(){
        return this.items;
    }
    public List<FluidStack> getFluids(){
        return this.fluids;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, items);
        ingredients.setOutputLists(VanillaTypes.ITEM, items);
        ingredients.setInputs(VanillaTypes.FLUID, fluids);
        ingredients.setOutputs(VanillaTypes.FLUID, fluids);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = 4;
        int yPos = 18*getIngredientRows()+2;

        if(!hasNoPages()){
            button.displayString = I18n.format(BUTTON_TRANSLATION_KEY);
            button.x = (recipeWidth - button.width) / 2;
            button.y = yPos;
            button.drawButton(minecraft, mouseX, mouseY, 1);
            yPos=yPos+22;
        }

        for (String line : lines) {
            minecraft.fontRenderer.drawString(line,xPos,yPos, Color.black.getRGB());
            yPos=yPos+minecraft.fontRenderer.FONT_HEIGHT;
        }
    }

    public boolean hasNoPages() {
        Entry entry = ManualRegistry.get(id);
        return entry == null || entry.getPages().isEmpty();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if(button.mousePressed(minecraft,mouseX,mouseY)){
            HistoryTracker.get().clear();
            GuiUnconfusion guiUnconfusion = new GuiUnconfusion();
            guiUnconfusion.jumpTo(this.id);
            Minecraft.getMinecraft().displayGuiScreen(guiUnconfusion);
            return true;
        }else{
            return false;
        }
    }
    public int getIngredientRows(){
        return (items.size()+fluids.size()-1)/9+1;
    }
}
