package io.bluebeaker.legacyponder.jeiplugin;

import io.bluebeaker.legacyponder.LegacyPonder;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class PonderRecipeCategory implements IRecipeCategory<PonderRecipeWrapper> {
    public static final int recipeWidth = 160;
    public static final int recipeHeight = 125;
    public static final String UID = "legacyponder.ponder";
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotBackground;
    private final FluidStackRenderer fluidStackRenderer = new FluidStackRenderer();

    public PonderRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(160, 125);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.BOOKSHELF));
        this.slotBackground = guiHelper.getSlotDrawable();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("gui.category.legacyponder");
    }

    @Override
    public String getModName() {
        return LegacyPonder.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PonderRecipeWrapper ponderRecipeWrapper, IIngredients ingredients) {
        int itemCount = ponderRecipeWrapper.getItems().size();
        int fluidCount = ponderRecipeWrapper.getFluids().size();

        int xPos = (recipeWidth - Math.min(9,itemCount+fluidCount)*18) / 2;
        int ingredientIndex = 0;

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        for (int i = 0; i < itemCount; i++) {
            guiItemStacks.init(i, true, xPos+ingredientIndex%9*18, ingredientIndex/9*18);
            guiItemStacks.setBackground(i, slotBackground);
            ingredientIndex++;
        }
        guiItemStacks.set(ingredients);

        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        for (int j = 0; j < fluidCount; j++) {
            guiFluidStackGroup.init(j, true, fluidStackRenderer,xPos+ingredientIndex%9*18, ingredientIndex/9*18,18,18,1,1);
            guiFluidStackGroup.setBackground(j, slotBackground);
            ingredientIndex++;
        }
        guiFluidStackGroup.set(ingredients);
    }
}
