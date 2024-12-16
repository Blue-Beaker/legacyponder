package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class PonderJEIPlugin implements IModPlugin {

    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new PonderRecipeCategory(guiHelper));
    }
    public void register(IModRegistry registry){
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipes(PonderRecipeRegistry.getRecipes(jeiHelpers),PonderRecipeCategory.UID);
    }
}
