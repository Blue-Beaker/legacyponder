package io.bluebeaker.legacyponder.jeiplugin;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import javax.annotation.Nullable;

@JEIPlugin
public class PonderJEIPlugin implements IModPlugin {

    @Nullable
    public static IJeiRuntime runtime;

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime=jeiRuntime;
    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new UnconfusionRecipeCategory(guiHelper));
    }
    public void register(IModRegistry registry){
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipes(PonderRecipeRegistry.getRecipes(jeiHelpers), UnconfusionRecipeCategory.UID);
    }
}
